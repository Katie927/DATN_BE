package com.DATN.Bej.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.DATN.Bej.config.VNPayConfig;
import com.DATN.Bej.service.payment.VNPayService;

@Controller
@RequestMapping("/payment")
public class PaymentController {
    @Autowired
    private VNPayService vnPayService;

    @GetMapping("/home")
    public String home() {
        return "index";
    }


}
