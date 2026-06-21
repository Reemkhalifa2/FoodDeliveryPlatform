package com.example.FoodDeliveryPlatformDemo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment extends BaseEntity{
    private String paymentMethod;
    private String status;
    private Double amount;
    private String transactionRef;
    private Date processedAt;

    @OneToOne
    private Order order;
}
