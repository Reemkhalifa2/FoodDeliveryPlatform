package com.example.FoodDeliveryPlatformDemo.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Delivery extends BaseEntity{
    private Integer trackingCode;
    private String status;
    private Date assignedAt;
    private Date pickedUpAt;
    private Date deliveredAt;

    @OneToOne
    private Order order;
    @ManyToOne
    private DeliveryDriver deliveryDriver;



}
