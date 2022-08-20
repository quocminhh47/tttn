package com.nashtech.ecommercialwebsite.services.impl;

import com.nashtech.ecommercialwebsite.data.entity.*;
import com.nashtech.ecommercialwebsite.data.repository.AccountRepo;
import com.nashtech.ecommercialwebsite.data.repository.BillRepository;
import com.nashtech.ecommercialwebsite.data.repository.ProductRepository;
import com.nashtech.ecommercialwebsite.data.repository.StaffRepo;
import com.nashtech.ecommercialwebsite.dto.request.BillRequest;
import com.nashtech.ecommercialwebsite.dto.request.CartItemUpdateDto;
import com.nashtech.ecommercialwebsite.dto.response.*;
import com.nashtech.ecommercialwebsite.exceptions.BadRequestException;
import com.nashtech.ecommercialwebsite.exceptions.OutOfStockException;
import com.nashtech.ecommercialwebsite.exceptions.ResourceNotFoundException;
import com.nashtech.ecommercialwebsite.services.AuthenticationFacadeService;
import com.nashtech.ecommercialwebsite.services.BillService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.nashtech.ecommercialwebsite.utils.AppConstants.*;

@Service
@AllArgsConstructor
public class BillServiceImpl implements BillService {

    private final BillRepository billRepository;

    private final ModelMapper mapper;

    private final ProductRepository productRepository;

    private final AccountRepo accountRepo;

    private final AuthenticationFacadeService authenticationFacade;

    private final StaffRepo staffRepo;


    @Override
    public BillResponse orderProducts(BillRequest billRequest) {
        Account account = authenticationFacade.getAccount();
        Customer customer = account.getCustomer();

        List<Product> products = productRepository.findAll();
        Map<Integer, Product> productMap = products.stream()
                .collect(Collectors.toMap(Product::getId, Function.identity()));
        List<Product> listProductOrder = new ArrayList<>();
        Bill bill = new Bill();
        bill.setCreateDate(new Date(Calendar.getInstance().getTimeInMillis()));
        bill.setCustomer(customer);

        BillResponse billResponse = new BillResponse();

        billRequest.getCartDetails().forEach(item -> {
            if (!productMap.containsKey(item.getProductId())) {
                throw new ResourceNotFoundException(
                        String.format("Product with ID: %s not found", item.getProductId()));
            }
            Product product = productMap.get(item.getProductId());
            if (product.getQuantity() < item.getProductQuantity())
                throw new OutOfStockException("Product quantity order is greater than its available: " + product.getQuantity());
            BillDetail billDetail = new BillDetail();
            billDetail.setId(new BillDetailId(bill.getId(), item.getProductId()));
            billDetail.setProduct(product);
            billDetail.setBill(bill);
            billDetail.setQuantity(item.getProductQuantity());
            billDetail.setPrice(product.getPrice()); //price per one product

            billResponse.getCartDetails().add(mapToBillItemResponse(item, product));

            bill.getBillDetails().add(billDetail);

            product.setQuantity(product.getQuantity() - item.getProductQuantity());
            listProductOrder.add(product);

        });

        billResponse.setPhone(customer.getPhone());
        billResponse.setAddress(customer.getAddress());
        billResponse.setFirstName(customer.getFirstName());
        billResponse.setLastName(customer.getLastName());
        billResponse.setEmail(account.getEmail());

        int billTotalPrice = bill.getBillDetails().stream()
                .mapToInt(s -> (s.getQuantity() * s.getPrice()))
                .sum();

        bill.setPriceTotal(billTotalPrice);
        billResponse.setPriceTotal(bill.getPriceTotal());

        //save bill & bill items
        billRepository.save(bill);
        billResponse.setBillId(bill.getId());

        listProductOrder.stream().forEach(product -> productRepository.save(product));
        return billResponse;
    }

    @Override
    public BillResponse getBillById(int billId) {

        Bill bill = billRepository.findById(billId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Bill %s not found", billId)));

        BillResponse billResponse = convertBillToBillResponse(bill);
        Optional<Staff> approvedStaff = Optional.ofNullable(bill.getApprovedStaff());
        if (approvedStaff.isEmpty()) {
            billResponse.setApprovedStaff("Not approved yet !");
        } else {
            billResponse.setApprovedStaff(bill.getApprovedStaff().getFirstName() + " " + bill.getApprovedStaff().getLastName());
        }

        Optional<Staff> deliveryStaff = Optional.ofNullable(bill.getShipper());
        if (deliveryStaff.isEmpty()) {
            billResponse.setDeliveryStaff("Not assigned yet !");
        } else {
            billResponse.setApprovedStaff(bill.getApprovedStaff().getFirstName() + " " + bill.getApprovedStaff().getLastName());
        }
        return billResponse;
    }

    @Override
    public BillResponse getSingleBillDetail(int billId) {
        Bill bill = billRepository.findById(billId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Bill %s not found", billId)));

        BillResponse billResponse = convertBillToBillResponse(bill);
        Optional<Staff> approvedStaff = Optional.ofNullable(bill.getApprovedStaff());
        if (approvedStaff.isEmpty()) {
            billResponse.setApprovedStaff("Not approved yet !");
        } else {
            billResponse.setApprovedStaff(bill.getApprovedStaff().getFirstName() + " " + bill.getApprovedStaff().getLastName());
        }

        Optional<Staff> deliveryStaff = Optional.ofNullable(bill.getShipper());
        if (deliveryStaff.isEmpty()) {
            billResponse.setDeliveryStaff("Not assigned yet !");
        } else {
            billResponse.setDeliveryStaff(deliveryStaff.get().getFirstName() + " " + deliveryStaff.get().getLastName());
//            billResponse.setApprovedStaff(bill.getApprovedStaff().getFirstName() + " " + bill.getApprovedStaff().getLastName());
        }

        return billResponse;

    }

    @Override
    public List<BillDetailReponse> getBillsByCustomer() {
        List<BillDetailReponse> billResponseList = new ArrayList<>();

        Account account = authenticationFacade.getAccount();

        List<Bill> bills = billRepository.findByCustomer(account.getCustomer());

        bills.forEach(bill -> {
            BillDetailReponse billDetailReponse = mapper.map(bill, BillDetailReponse.class);
            billDetailReponse.setUsername(account.getEmail());
            billResponseList.add(billDetailReponse);
        });
        return billResponseList;
    }

    @Override
    public BillResponse changeBilStatus(int billId, String status) {
        int statusValueConverted = getBillStatus(status);

        Bill bill = billRepository.findById(billId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Bill Id: %s not found", billId)));
        bill.setStatus(statusValueConverted);

        Account currentAccount = authenticationFacade.getAccount();
        bill.setApprovedStaff(currentAccount.getStaff());
        Bill updatedBill = billRepository.save(bill);

        BillResponse billResponse = convertBillToBillResponse(updatedBill);
        billResponse.setApprovedStaff(bill.getApprovedStaff().getFirstName() + " " + bill.getApprovedStaff().getLastName());
        return billResponse;
    }

    @Override
    public BillPaginationResponse getAllBills(int pageNo,
                                              int pageSize,
                                              String sortBy,
                                              String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        //create pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Bill> bills = billRepository.findAll(pageable);

        return getContent(bills);

    }

    @Override
    public BillPaginationResponse getAllBillsByStatus(int pageNo,
                                                      int pageSize,
                                                      String sortBy,
                                                      String sortDir,
                                                      String status) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        int statusValueConverted = getBillStatus(status);

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Bill> bills = billRepository.findBillByStatus(pageable, statusValueConverted);

        return getContent(bills);
    }

    @Override
    public BillReportResponse getSaleReportByDateRange(String dateStart, String dateEnd) {
        LocalDate start = LocalDate.parse(dateStart);
        LocalDate end = LocalDate.parse(dateEnd);
        if (start.isAfter(end)) {
            throw new BadRequestException("Date start is must before date end!!");
        }
        Long sale = billRepository.getSaleByRangeOfDate(dateStart, dateEnd);
        if (sale == null) {
            return new BillReportResponse(null, null);
        }
        List<BillDetailReponse> billResponseList = new ArrayList<>();
        billRepository.getBillsByDate(dateStart, dateEnd).forEach(bill -> {
            BillDetailReponse billDetailReponse = mapper.map(bill, BillDetailReponse.class);
            billDetailReponse.setUsername(bill.getCustomer().getAccount().getEmail());
            billResponseList.add(billDetailReponse);
        });

        return new BillReportResponse(sale, billResponseList);
    }

    @Override
    public BillResponse setDeliveryStaff(int billId, String staffEmail) {
        Bill bill = billRepository.findById(billId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Bill Id: %s not found", billId)));
       Account account = accountRepo.findAccountByEmail(staffEmail).orElseThrow(
               () -> new ResourceNotFoundException("Staff :" + staffEmail + "not found"));
       if(account.getRole().getRoleName().equalsIgnoreCase("user"))
           throw new BadRequestException("Only staff be assigned for this");
       Staff staff =account.getStaff();

        bill.setShipper(staff);

        Optional<Staff> approvedStaff = Optional.ofNullable(bill.getApprovedStaff());
        if (approvedStaff.isEmpty() || bill.getStatus() == 0) {
            throw new BadRequestException("This order must be approved before assigned to be deliveried");
        }
        Bill savedBill = billRepository.save(bill);
        BillResponse response = convertBillToBillResponse(savedBill);
        response.setApprovedStaff(savedBill.getApprovedStaff().getFirstName() + " " + savedBill.getApprovedStaff().getLastName());
        response.setDeliveryStaff(savedBill.getShipper().getFirstName() + " " + savedBill.getShipper().getLastName());
        return response;
    }

    private int getBillStatus(String status) {
        int statusValueConverted = 0;
        switch (status.toLowerCase()) {
            case "accepted":
                statusValueConverted = BILL_STATUS_ACCEPTED;
                break;
            case "purchased":
                statusValueConverted = BILL_STATUS_PURCHASED;
                break;
            case "canceled":
                statusValueConverted = BILL_STATUS_CANCELED;
                break;
            case "unsolved":
                statusValueConverted = BILL_STATUS_UNSOLVED;
                break;

        }
        return statusValueConverted;
    }

    private BillItemResponse mapToBillItemResponse(CartItemUpdateDto cartItemUpdateDto, Product product) {
        BillItemResponse billItemResponse = mapper.map(cartItemUpdateDto, BillItemResponse.class);
        billItemResponse.setProductName(product.getName());
        billItemResponse.setProductPrice(product.getPrice());
        return billItemResponse;
    }

    private BillPaginationResponse getContent(Page<Bill> bills) {
        List<Bill> billList = bills.getContent();
        List<BillDetailReponse> billContent = new ArrayList<>();

        billList.forEach(bill -> {
            BillDetailReponse billDetailReponse = mapper.map(bill, BillDetailReponse.class);
            billDetailReponse.setUsername(bill.getCustomer().getAccount().getEmail());
            billContent.add(billDetailReponse);
        });

        return BillPaginationResponse.builder()
                .billContent(billContent)
                .pageNo(bills.getNumber())
                .pageSize(bills.getSize())
                .totalElements(bills.getTotalElements())
                .totalPages(bills.getTotalPages())
                .last(bills.isLast())
                .build();
    }

    private BillResponse convertBillToBillResponse(Bill bill) {
        Customer customer = bill.getCustomer();
        Account account = customer.getAccount();

        BillResponse billResponse = BillResponse.builder()
                .billId(bill.getId())
                .cartDetails(new ArrayList<>())
                .priceTotal(bill.getPriceTotal())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .phone(customer.getPhone())
                .address(customer.getAddress())
                .email(account.getEmail())
                .build();

        bill.getBillDetails().forEach(item -> {
            BillItemResponse itemResponse = BillItemResponse.builder()
                    .productId(item.getProduct().getId())
                    .productName(item.getProduct().getName())
                    .productQuantity(item.getQuantity())
                    .productPrice(item.getPrice())
                    .build();

            billResponse.getCartDetails().add(itemResponse);
        });

        return billResponse;
    }
}
