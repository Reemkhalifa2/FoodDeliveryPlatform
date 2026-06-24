package com.example.FoodDeliveryPlatformDemo.exceptions;

public class RestaurantNotFoundException extends RuntimeException{
    public RestaurantNotFoundException() {
        super("Restaurant not found");
    }

}
