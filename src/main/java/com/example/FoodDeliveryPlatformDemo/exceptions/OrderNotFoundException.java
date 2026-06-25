package com.example.FoodDeliveryPlatformDemo.exceptions;

public class OrderNotFoundException extends RuntimeException{
    public OrderNotFoundException( ) {
        super("Customer Not Found");
    }

}
