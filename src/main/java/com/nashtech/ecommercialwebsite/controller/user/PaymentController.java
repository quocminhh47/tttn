//package com.nashtech.ecommercialwebsite.controller.user;
//
//import com.nashtech.ecommercialwebsite.config.PaypalPaymentIntent;
//import com.nashtech.ecommercialwebsite.config.PaypalPaymentMethod;
//import com.nashtech.ecommercialwebsite.services.impl.PaypalService;
//import com.nashtech.ecommercialwebsite.utils.AppConstants;
//import com.paypal.api.payments.Links;
//import com.paypal.api.payments.Payment;
//import com.paypal.base.rest.PayPalRESTException;
//import lombok.AllArgsConstructor;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.servlet.http.HttpServletRequest;
//
//@RestController
//@AllArgsConstructor
//@RequestMapping("/pay")
//public class PaymentController {
//
//    public static final String URL_PAYPAL_SUCCESS = "pay/success";
//    public static final String URL_PAYPAL_CANCEL = "pay/cancel";
//
//    private final PaypalService paypalService;
//
//    @PostMapping()
//    public String purchaseOrder(HttpServletRequest request, @RequestParam("price") double price) {
//        String cancelUrl = AppConstants.getBaseURL(request) + "/" + URL_PAYPAL_CANCEL;
//        String successUrl = AppConstants.getBaseURL(request) + "/" + URL_PAYPAL_SUCCESS;
//        try {
//            Payment payment = paypalService.createPayment(
//                    price,
//                    "USD",
//                    PaypalPaymentMethod.paypal,
//                    PaypalPaymentIntent.sale,
//                    "payment description",
//                    cancelUrl,
//                    successUrl);
//            for(Links links : payment.getLinks()){
//                if(links.getRel().equals("approval_url")){
//                    return "redirect:" + links.getHref();
//                }
//            }
//        } catch (PayPalRESTException e) {
//            return "Purchase failed";
//        }
//        return "success";
//
//    }
//
//}
