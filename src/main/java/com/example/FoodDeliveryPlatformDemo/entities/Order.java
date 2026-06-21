package com.example.FoodDeliveryPlatformDemo.entities;

import lombok.Data;

import java.util.Date;

public class Order extends BaseEntity{
    private String orderCode;
    private Date orderDate;
    private String status;
    private Double subtotal;
    private Double deliveryFee;
    private Double discountAmount;
    private Double totalAmount;
    private String deliveryNotes;
}
