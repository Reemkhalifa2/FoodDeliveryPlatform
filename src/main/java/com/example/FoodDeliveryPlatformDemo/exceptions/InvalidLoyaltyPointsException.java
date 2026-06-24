package com.example.FoodDeliveryPlatformDemo.exceptions;

public class InvalidLoyaltyPointsException extends RuntimeException{
    public InvalidLoyaltyPointsException(String message) {
        super(message);
    }
}
