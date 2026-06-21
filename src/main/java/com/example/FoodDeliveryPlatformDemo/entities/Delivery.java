package com.example.FoodDeliveryPlatformDemo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Delivery extends BaseEntity{
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer trackingCode;
    private String status;
    private Date assignedAt;
    private Date pickedUpAt;
    private Date deliveredAt;



}
