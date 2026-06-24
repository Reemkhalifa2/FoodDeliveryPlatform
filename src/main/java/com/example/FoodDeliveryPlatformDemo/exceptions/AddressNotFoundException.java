package com.example.FoodDeliveryPlatformDemo.exceptions;

public class AddressNotFoundException extends RuntimeException{
    public AddressNotFoundException() {
        super("Address not found");
    }
}

