package com.example.FoodDeliveryPlatformDemo.dto.response;

import com.example.FoodDeliveryPlatformDemo.dto.summary.MenuItemSummaryDTO;
import com.example.FoodDeliveryPlatformDemo.entities.OrderItem;
import com.example.FoodDeliveryPlatformDemo.entities.OrderStatusHistory;
import com.example.FoodDeliveryPlatformDemo.enums.OrderStatus;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class OrderHistoryResponseDTO {
    private Integer id;
    private OrderStatus status;
    private Date date;
    public static OrderHistoryResponseDTO toResponse(OrderStatusHistory orderStatusHistory) {
        OrderHistoryResponseDTO dto = new OrderHistoryResponseDTO();

        dto.setId(orderStatusHistory.getId());
        dto.setDate(orderStatusHistory.getCreatedDate());
        dto.setStatus(orderStatusHistory.getStatus());

        return dto;
    }

    public static List<OrderHistoryResponseDTO> toResponse(List<OrderStatusHistory> orderStatusHistories) {
        List<OrderHistoryResponseDTO> dtos = new ArrayList<>();

        for (OrderStatusHistory entity : orderStatusHistories) {
            dtos.add(OrderHistoryResponseDTO.toResponse(entity));
        }

        return dtos;
    }
}
