package com.example.FoodDeliveryPlatformDemo.exceptions;

public class InvalidOrderStateException extends RuntimeException{
    public InvalidOrderStateException() {
        super("Invalid order state");
    }

    public InvalidOrderStateException(String message) {
        super(message);
    }
}
