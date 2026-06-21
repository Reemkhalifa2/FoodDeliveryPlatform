package com.example.FoodDeliveryPlatformDemo.entities;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Customer extends Person{
    private Integer loyaltyPoints;
    private String customerCode;

}
