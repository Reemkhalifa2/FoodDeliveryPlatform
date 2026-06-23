package com.example.FoodDeliveryPlatformDemo.dto.request;

import com.example.FoodDeliveryPlatformDemo.entities.MenuItem;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MenuItemRequestDTO {
    private String name;
    private String description;
    private Double price;
    private Boolean isVegetarian;
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
