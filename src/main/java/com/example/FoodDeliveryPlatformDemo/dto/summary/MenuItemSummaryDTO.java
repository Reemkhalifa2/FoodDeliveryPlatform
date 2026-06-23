package com.example.FoodDeliveryPlatformDemo.dto.summary;

import com.example.FoodDeliveryPlatformDemo.entities.MenuItem;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MenuItemSummaryDTO {
    private String name;
    private Double price;

    public static MenuItemSummaryDTO toSummary(MenuItem menuItem) {
        MenuItemSummaryDTO dto = new MenuItemSummaryDTO();

        dto.setName(menuItem.getName());
        dto.setPrice(menuItem.getPrice());

        return dto;
    }
}
