package com.example.FoodDeliveryPlatformDemo.exceptions;

public class OrderNotFoundException extends RuntimeException{
    public OrderNotFoundException( ) {
        super("Order Not Found");
    }

}
