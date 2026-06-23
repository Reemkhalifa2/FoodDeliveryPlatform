package com.example.FoodDeliveryPlatformDemo.dto.response;

import com.example.FoodDeliveryPlatformDemo.entities.Payment;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaymentResponseDTO {
    private String paymentMethod;
    private String status;
    private Double amount;

    public static PaymentResponseDTO toResponse(Payment payment) {
        if (payment == null) {
            return null;
        }

        PaymentResponseDTO dto = new PaymentResponseDTO();

        dto.setPaymentMethod(payment.getPaymentMethod());
        dto.setStatus(payment.getStatus());
        dto.setAmount(payment.getAmount());

        return dto;
    }

    public static List<PaymentResponseDTO> toResponse(List<Payment> payments) {
        List<PaymentResponseDTO> dtos = new ArrayList<>();

        if (payments != null) {
            for (Payment payment : payments) {
                dtos.add(toResponse(payment));
            }
        }

        return dtos;
    }
}
