package com.example.FoodDeliveryPlatformDemo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
    private List<CustomerAddress> customerAddresses;
    @OneToMany
    private List<Order> orders;
    @OneToMany
    private List<Review> reviews;

    @ManyToOne
    private Customer customer;

}
