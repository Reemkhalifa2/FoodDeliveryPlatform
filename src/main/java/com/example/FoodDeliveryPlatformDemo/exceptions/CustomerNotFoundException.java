package com.example.FoodDeliveryPlatformDemo.exceptions;

public class CustomerNotFoundException extends RuntimeException{
    public CustomerNotFoundException(Integer id) {
        super("Customer not found with id: " + id);
    }
}
