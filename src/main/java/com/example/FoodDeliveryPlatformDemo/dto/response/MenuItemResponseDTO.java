package com.example.FoodDeliveryPlatformDemo.dto.response;

import com.example.FoodDeliveryPlatformDemo.entities.MenuItem;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MenuItemResponseDTO {
    private Integer id;
    private String name;
    private String description;
    private Double price;
    private Boolean isAvailable;
    private Boolean isVegetarian;
    private Double calories;

    public static MenuItemResponseDTO toResponse(MenuItem menuItem) {
        MenuItemResponseDTO dto = new MenuItemResponseDTO();
        dto.setId(menuItem.getId());
        dto.setIsAvailable(menuItem.getIsAvailable());
        dto.setName(menuItem.getName());
        dto.setDescription(menuItem.getDescription());
        dto.setPrice(menuItem.getPrice());
        dto.setIsVegetarian(menuItem.getIsVegetarian());
        dto.setCalories(menuItem.getCalories());

        return dto;
    }

    public static List<MenuItemResponseDTO> toResponse(List<MenuItem> menuItems) {
        List<MenuItemResponseDTO> dtos = new ArrayList<>();

        for (MenuItem entity : menuItems) {
            dtos.add(toResponse(entity));
        }

        return dtos;
    }

}
