package com.example.FoodDeliveryPlatformDemo.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Customer extends Person{
    private Integer loyaltyPoints;
    private String customerCode;

    @OneToMany
    List<CustomerAddress> customerAddresses;
    @OneToMany
    private List<Order> orders;
    @OneToMany
    private List<Review> reviews;

}
