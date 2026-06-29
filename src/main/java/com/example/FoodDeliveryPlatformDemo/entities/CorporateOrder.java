package com.example.FoodDeliveryPlatformDemo.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CorporateOrder extends BaseEntity {
    private String corporateCode;
    private String companyName;
    private Double costCenter;
    private Date orderDate;
    private String status;
    private Double totalAmount;

    @ManyToOne
    private Restaurant restaurant;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items;
}
