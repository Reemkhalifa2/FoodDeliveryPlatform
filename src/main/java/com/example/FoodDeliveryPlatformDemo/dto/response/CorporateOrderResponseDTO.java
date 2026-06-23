package com.example.FoodDeliveryPlatformDemo.dto.response;

import com.example.FoodDeliveryPlatformDemo.dto.summary.OrderItemSummaryDTO;
import com.example.FoodDeliveryPlatformDemo.entities.CorporateOrder;
import com.example.FoodDeliveryPlatformDemo.entities.CustomerAddress;
import com.example.FoodDeliveryPlatformDemo.entities.OrderItem;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
public class CorporateOrderResponseDTO {
    private String companyName;
    private String status;
    private Double totalAmount;
    private List<OrderItemSummaryDTO> items;

    public static CorporateOrderResponseDTO toResponse(CorporateOrder order) {
        CorporateOrderResponseDTO dto = new CorporateOrderResponseDTO();
        dto.setCompanyName(order.getCompanyName());
        dto.setStatus(order.getStatus());
        dto.setTotalAmount(order.getTotalAmount());
        dto.setItems(OrderItemSummaryDTO.toSummary(order.getItems()));
        return dto;
    }

    public static List<CorporateOrderResponseDTO> toResponse(List<CorporateOrder> orders) {
        List<CorporateOrderResponseDTO> dtos = new ArrayList<>();
        for (CorporateOrder entity : orders) {
            dtos.add(toResponse(entity));
        }
        return dtos;
    }


}
