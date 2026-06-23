package com.example.FoodDeliveryPlatformDemo.dto.request;

import com.example.FoodDeliveryPlatformDemo.entities.MenuItem;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MenuItemRequestDTO {
    @NotBlank(message = "Name is required")
    private String name;
    private String description;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0")
    private Double price;

    @NotNull(message = "Vegetarian flag is required")
    private Boolean isVegetarian;

    @NotNull(message = "Calories is required")
    @PositiveOrZero(message = "Calories must be 0 or more")
    private Double calories;

    public static MenuItem toEntity(MenuItemRequestDTO dto) {
        MenuItem menuItem = new MenuItem();
        menuItem.setName(dto.getName());
        menuItem.setDescription(dto.getDescription());
        menuItem.setPrice(dto.getPrice());
        menuItem.setIsVegetarian(dto.getIsVegetarian());
        menuItem.setCalories(dto.getCalories());
        return menuItem;
    }


}
