package com.example.FoodDeliveryPlatformDemo.dto.response;

import com.example.FoodDeliveryPlatformDemo.entities.Restaurant;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class RestaurantResponseDTO {
    private String name;
    private String description;
    private String cuisineType;
    private Date openingTime;
    private Date closingTime;
    private Integer minOrderAmount;
    private Double deliveryFee;

    public static RestaurantResponseDTO toResponse(Restaurant restaurant) {
        RestaurantResponseDTO dto = new RestaurantResponseDTO();

        dto.setName(restaurant.getName());
        dto.setDescription(restaurant.getDescription());
        dto.setCuisineType(restaurant.getCuisineType());
        dto.setOpeningTime(restaurant.getOpeningTime());
        dto.setClosingTime(restaurant.getClosingTime());
        dto.setMinOrderAmount(restaurant.getMinOrderAmount());
        dto.setDeliveryFee(restaurant.getDeliveryFee());

        return dto;
    }

    public static List<RestaurantResponseDTO> toResponse(List<Restaurant> restaurants) {
        List<RestaurantResponseDTO> dtos = new ArrayList<>();

        for (Restaurant entity : restaurants) {
            dtos.add(toResponse(entity));
        }

        return dtos;
    }


}
