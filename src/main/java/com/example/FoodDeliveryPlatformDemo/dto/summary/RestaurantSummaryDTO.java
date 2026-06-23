package com.example.FoodDeliveryPlatformDemo.dto.summary;

import com.example.FoodDeliveryPlatformDemo.entities.Restaurant;
import lombok.Data;

@Data
public class RestaurantSummaryDTO {
    private String name;
    private String cuisineType;

    public static RestaurantSummaryDTO toSummary(Restaurant restaurant) {
        RestaurantSummaryDTO dto = new RestaurantSummaryDTO();

        dto.setName(restaurant.getName());
        dto.setCuisineType(restaurant.getCuisineType());

        return dto;
    }
}
