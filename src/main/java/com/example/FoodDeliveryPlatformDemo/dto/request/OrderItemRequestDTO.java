package com.example.FoodDeliveryPlatformDemo.dto.request;

import com.example.FoodDeliveryPlatformDemo.entities.MenuItem;
import com.example.FoodDeliveryPlatformDemo.entities.OrderItem;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemRequestDTO {
    @Min(value = 1)
    private Integer quantity;
    private String specialInstructions;
    @NotNull
    private Integer menuItemId;

    public static OrderItem toEntity(OrderItemRequestDTO dto) {

        OrderItem orderItem = new OrderItem();
        MenuItem menuItem = new MenuItem();
        menuItem.setId(dto.getMenuItemId());
        orderItem.setMenuItem(menuItem);
        orderItem.setUnitPrice(menuItem.getPrice());
        orderItem.setQuantity(dto.getQuantity());
        orderItem.setUpdatedDate(new Date());
        orderItem.setSpecialInstructions(dto.getSpecialInstructions());

        return orderItem;
    }


}
