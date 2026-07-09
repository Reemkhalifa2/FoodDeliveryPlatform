package com.example.FoodDeliveryPlatformDemo.dto.response;

import com.example.FoodDeliveryPlatformDemo.entities.Restaurant;
import com.example.FoodDeliveryPlatformDemo.enums.OrderStatus;
import com.example.FoodDeliveryPlatformDemo.dto.summary.OrderItemSummaryDTO;
import com.example.FoodDeliveryPlatformDemo.entities.Order;
import com.example.FoodDeliveryPlatformDemo.utilities.HelperUtils;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class OrderResponseDTO {
    private Integer id;
    private String orderCode;
    private OrderStatus status;
    private Double deliveryFee;
    private Double subtotal;
    private Double discountAmount;
    private Double totalAmount;
    private String restaurant;
    private DeliveryResponseDTO deliveryResponseDTO;
    private List<OrderItemSummaryDTO> orderItems;
    public static OrderResponseDTO toResponse(Order order) {
        OrderResponseDTO dto = new OrderResponseDTO();
        dto.setId(order.getId());
        dto.setOrderCode(order.getOrderCode());
        dto.setStatus(order.getStatus());
        dto.setDeliveryFee(order.getDeliveryFee());
        dto.setSubtotal(order.getSubtotal());
        dto.setDiscountAmount(order.getDiscountAmount());
        dto.setTotalAmount(order.getTotalAmount());
        dto.setRestaurant(order.getRestaurant().getName());
        dto.setDeliveryResponseDTO(DeliveryResponseDTO.toResponse(order
                .getDelivery()));
        if (HelperUtils.isNotNull(order.getOrderItems())) {
            dto.setOrderItems(OrderItemSummaryDTO.toSummary(order.getOrderItems()));
        }
        return dto;
    }

    public static List<OrderResponseDTO> toResponse(List<Order> orders) {
        List<OrderResponseDTO> dtos = new ArrayList<>();

        for (Order entity : orders) {
            dtos.add(toResponse(entity));
        }

        return dtos;
    }

}
