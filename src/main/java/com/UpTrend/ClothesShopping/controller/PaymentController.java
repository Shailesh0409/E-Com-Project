package com.UpTrend.ClothesShopping.controller;


import com.UpTrend.ClothesShopping.service.RazorpayService;
import com.razorpay.Order;
import com.razorpay.RazorpayException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "*") // allow frontend calls
@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private RazorpayService razorpayService;

    @PostMapping("/create-order")
    public Map<String, Object> createOrder(@RequestParam BigDecimal amount) throws RazorpayException {
        Order order = razorpayService.createOrder(amount, "INR", "txn_" + System.currentTimeMillis());

        Map<String, Object> response = new HashMap<>();
        response.put("orderId", order.get("id"));
        response.put("amount", order.get("amount"));
        response.put("currency", order.get("currency"));
        return response;
    }
}
