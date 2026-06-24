package com.example.FoodDeliveryPlatformDemo.exceptions;

public class OwnerNotFoundException extends RuntimeException{
    public OwnerNotFoundException() {
        super("Owner not found");
    }
}
