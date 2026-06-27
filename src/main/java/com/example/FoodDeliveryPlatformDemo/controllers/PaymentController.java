package com.example.FoodDeliveryPlatformDemo.controllers;

import com.example.FoodDeliveryPlatformDemo.dto.response.PaymentResponseDTO;
import com.example.FoodDeliveryPlatformDemo.entities.Order;
import com.example.FoodDeliveryPlatformDemo.entities.Payment;
import com.example.FoodDeliveryPlatformDemo.enums.OrderStatus;
import com.example.FoodDeliveryPlatformDemo.repositories.PaymentRepository;
import com.example.FoodDeliveryPlatformDemo.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    PaymentService paymentService;

    @GetMapping("/analytics/by-method")
    public ResponseEntity<Map<String, Double>> getAnalytics() {
        return ResponseEntity.ok(paymentService.getPaymentAnalyticsByMethod());
    }
    @GetMapping
    public Page<Payment> getPayment(
            @RequestParam String paymentMethod,
            @RequestParam String status,
            @RequestParam String from,
            @RequestParam String to,
            @RequestParam Integer page,
            @RequestParam Integer size
    ) {

        return paymentService.getPayments(
                paymentMethod,
                status,
                from,
                to,
                page,
                size
        );
    }


    @PostMapping("/order/{orderId}")
    public ResponseEntity<PaymentResponseDTO> processPayment(@PathVariable Integer orderId , @RequestParam String paymentMethod ){
        PaymentResponseDTO response = paymentService.processPayment(orderId,paymentMethod);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }
    @PutMapping("/{paymentId}/complete")
    public ResponseEntity<PaymentResponseDTO> markPaymentAsSuccessful(@PathVariable Integer paymentId){
        return ResponseEntity.ok(paymentService.markPaymentAsSuccessful(paymentId));
    }

    @PutMapping(" /{paymentId}/refund")
    public ResponseEntity<PaymentResponseDTO> refundPayment(@PathVariable Integer paymentId){
        return ResponseEntity.ok(paymentService.refundPayment(paymentId));
    }

}