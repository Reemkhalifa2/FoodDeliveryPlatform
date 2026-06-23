package com.example.FoodDeliveryPlatformDemo.dto.request;

import com.example.FoodDeliveryPlatformDemo.entities.Restaurant;
import com.example.FoodDeliveryPlatformDemo.entities.RestaurantOwner;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data@NoArgsConstructor
public class RestaurantRequestDTO {
    @NotBlank
    private String name;
    private String description;
    @NotBlank
    private String cuisineType;
    @NotBlank
    private Date openingTime;
    @NotBlank
    private Date closingTime;
    @NotNull
    @Min(value = 0)
    private Integer minOrderAmount;
    @NotNull
    @DecimalMin(value = "0.0")
    @DecimalMax(value = "0.8")
    private Double deliveryFee;

    public static Restaurant toEntity(RestaurantRequestDTO dto) {
        Restaurant restaurant = new Restaurant();
        restaurant.setName(dto.getName());
        restaurant.setDescription(dto.getDescription());
        restaurant.setCuisineType(dto.getCuisineType());
        restaurant.setOpeningTime(dto.getOpeningTime());
        restaurant.setClosingTime(dto.getClosingTime());
        restaurant.setMinOrderAmount(dto.getMinOrderAmount());
        restaurant.setDeliveryFee(dto.getDeliveryFee());
        return restaurant;
    }

}
