package com.example.FoodDeliveryPlatformDemo.dto.request;

import com.example.FoodDeliveryPlatformDemo.entities.Order;
import com.example.FoodDeliveryPlatformDemo.entities.OrderItem;
import com.example.FoodDeliveryPlatformDemo.utilities.HelperUtils;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDTO {
    @NotNull
    private Integer customerId;
    @NotNull
    private Integer restaurantId;
    @NotBlank
    private List<OrderItemRequestDTO> items;

    public static Order toEntity(OrderRequestDTO dto) {
        Order order = new Order();
        order.getCustomer().setId(dto.getCustomerId());
        order.getRestaurant().setId(dto.getRestaurantId());

        if (HelperUtils.isNotNull(dto.getItems())) {
            List<OrderItem> orderItems = new ArrayList<>();

            for (OrderItemRequestDTO itemDto : dto.getItems()) {
                OrderItem item = OrderItemRequestDTO.toEntity(itemDto);
                orderItems.add(item);
            }
            order.setOrderItems(orderItems);
        }

        return order;
    }


}
