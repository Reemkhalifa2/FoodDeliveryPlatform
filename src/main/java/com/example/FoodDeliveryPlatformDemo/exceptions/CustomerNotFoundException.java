package com.example.FoodDeliveryPlatformDemo.exceptions;

import javax.swing.*;

public class CustomerNotFoundException extends RuntimeException{
    public CustomerNotFoundException() {
        super("Customer not found");
    }
}
