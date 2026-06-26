package com.example.FoodDeliveryPlatformDemo.controllers;

import com.example.FoodDeliveryPlatformDemo.dto.response.PaymentResponseDTO;
import com.example.FoodDeliveryPlatformDemo.repositories.PaymentRepository;
import com.example.FoodDeliveryPlatformDemo.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    PaymentService paymentService;

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