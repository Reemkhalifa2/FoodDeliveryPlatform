package com.example.FoodDeliveryPlatformDemo.exceptions;

public class InvalidPaymentStatusException extends RuntimeException{
    public InvalidPaymentStatusException(String message){
        super(message);
    }
}
