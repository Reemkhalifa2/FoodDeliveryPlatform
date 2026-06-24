package com.example.FoodDeliveryPlatformDemo.exceptions;

public class NullRequestBodyException extends RuntimeException {
    public NullRequestBodyException(String message) {
        super(message);
    }
}
