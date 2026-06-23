package com.example.FoodDeliveryPlatformDemo.dto.summary;

import com.example.FoodDeliveryPlatformDemo.entities.Payment;
import lombok.Data;

@Data
public class PaymentSummaryDTO {
    private String paymentMethod;
    private String status;
    private Double amount;

    public static PaymentSummaryDTO toSummary(Payment payment) {
        if (payment == null) {
            return null;
        }

        PaymentSummaryDTO dto = new PaymentSummaryDTO();

        dto.setPaymentMethod(payment.getPaymentMethod());
        dto.setStatus(payment.getStatus());
        dto.setAmount(payment.getAmount());

        return dto;
    }

}
