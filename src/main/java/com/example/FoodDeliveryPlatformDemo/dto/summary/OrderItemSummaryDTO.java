package com.example.FoodDeliveryPlatformDemo.dto.summary;

import com.example.FoodDeliveryPlatformDemo.entities.OrderItem;
import lombok.Data;

@Data
public class OrderItemSummaryDTO {
    private Integer quantity;
    private Double totalPrice;

    public static OrderItemSummaryDTO toSummary(OrderItem orderItem) {
        OrderItemSummaryDTO dto = new OrderItemSummaryDTO();

        dto.setQuantity(orderItem.getQuantity());
        dto.setTotalPrice(orderItem.getTotalPrice());

        return dto;
    }
}