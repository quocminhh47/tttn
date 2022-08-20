package com.nashtech.ecommercialwebsite.services;

import com.nashtech.ecommercialwebsite.dto.request.BillRequest;
import com.nashtech.ecommercialwebsite.dto.response.BillDetailReponse;
import com.nashtech.ecommercialwebsite.dto.response.BillPaginationResponse;
import com.nashtech.ecommercialwebsite.dto.response.BillReportResponse;
import com.nashtech.ecommercialwebsite.dto.response.BillResponse;
import java.util.List;

public interface BillService {

    BillResponse orderProducts( BillRequest billRequest);

    BillResponse getBillById( int billId);

    BillResponse getSingleBillDetail(int billId);

    BillResponse changeBilStatus(int billId, String status) ;

    List<BillDetailReponse> getBillsByCustomer();

    BillPaginationResponse getAllBills(int pageNo,
                                       int pageSize,
                                       String sortBy,
                                       String sortDir);

    BillPaginationResponse getAllBillsByStatus(int pageNo,
                                               int pageSize,
                                               String sortBy,
                                               String sortDir,
                                               String status);
    BillReportResponse getSaleReportByDateRange(String dateStart, String dateEnd);

    BillResponse setDeliveryStaff(int billId, String staffEmail);
}
