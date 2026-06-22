package com.example.FoodDeliveryPlatformDemo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Review extends BaseEntity{
    private String targetType;
    private Integer rating;
    private String comment;

    @ManyToOne
    private Customer customer;
    @ManyToOne(optional = true)
    private Restaurant restaurant;
    @ManyToOne(optional = true)
    private DeliveryDriver deliveryDriver;
}
