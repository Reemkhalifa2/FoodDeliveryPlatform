package com.example.FoodDeliveryPlatformDemo.entities;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Restaurant extends BaseEntity{
    private String name;
    private String description;
    private String cuisineType;
    private Date openingTime;
    private Date closingTime;
    private Integer minOrderAmount;
    private Double deliveryFee;
    private Boolean acceptingOrders;
}
