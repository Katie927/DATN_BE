package com.DATN.Bej.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.DATN.Bej.config.VNPayConfig;
import com.DATN.Bej.service.payment.VNPayService;

import org.springframework.ui.Model;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
@RequestMapping("/payment")
public class PaymentController {
    @Autowired
    private VNPayService vnPayService;

    @GetMapping("/home")
    public String home() {
        return "index";
    }

    @PostMapping("/submitOrder")
    public String submidOrder(@RequestParam("amount") int orderTotal,
                            @RequestParam("orderInfo") String orderInfo,
                            HttpServletRequest request){
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        String vnpayUrl = vnPayService.createOrder(orderTotal, orderInfo, baseUrl, request);
        return "redirect:" + vnpayUrl;
    }

    @GetMapping("/vnpay-payment")
    public String GetMapping(HttpServletRequest request, Model model){
        int paymentStatus =vnPayService.orderReturn(request);

        String orderInfo = request.getParameter("vnp_OrderInfo");
        String paymentTime = request.getParameter("vnp_PayDate");
        String transactionId = request.getParameter("vnp_TransactionNo");
        String totalPrice = request.getParameter("vnp_Amount");

        model.addAttribute("orderId", orderInfo);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("paymentTime", paymentTime);
        model.addAttribute("transactionId", transactionId);

        return paymentStatus == 1 ? "ordersuccess" : "orderfail";
    }
    
}


// package com.DATN.Bej.controller;

// import com.DATN.Bej.config.VNPayService;
// import jakarta.servlet.http.HttpServletRequest;
// import lombok.RequiredArgsConstructor;
// import org.springframework.web.bind.annotation.*;

// import java.util.HashMap;
// import java.util.Map;

// @RestController
// @RequestMapping("/api/payment")
// @RequiredArgsConstructor
// public class PaymentController {

//     private final VNPayService vnPayService;

//     /**
//      * Tạo URL thanh toán VNPAY
//      * @param amount số tiền thanh toán (VND)
//      * @param orderInfo thông tin đơn hàng (mô tả ngắn)
//      * @param request thông tin HTTP request để lấy IP / base URL
//      * @return URL redirect tới VNPAY
//      */
//     @PostMapping("/create")
//     public Map<String, Object> createPayment(
//             @RequestParam("amount") int amount,
//             @RequestParam("orderInfo") String orderInfo,
//             HttpServletRequest request
//     ) {
//         String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
//         String paymentUrl = vnPayService.createOrder(amount, orderInfo, baseUrl, request);

//         Map<String, Object> response = new HashMap<>();
//         response.put("status", "success");
//         response.put("paymentUrl", paymentUrl);
//         return response;
//     }

//     /**
//      * Nhận kết quả trả về từ VNPAY (người dùng quay lại website)
//      */
//     @GetMapping("/vnpay-return")
//     public Map<String, Object> vnpayReturn(HttpServletRequest request) {
//         int paymentStatus = vnPayService.orderReturn(request);

//         Map<String, Object> response = new HashMap<>();
//         response.put("orderInfo", request.getParameter("vnp_OrderInfo"));
//         response.put("paymentTime", request.getParameter("vnp_PayDate"));
//         response.put("transactionId", request.getParameter("vnp_TransactionNo"));
//         response.put("amount", request.getParameter("vnp_Amount"));
//         response.put("status", paymentStatus == 1 ? "success" : "failed");

//         return response;
//     }
// }

