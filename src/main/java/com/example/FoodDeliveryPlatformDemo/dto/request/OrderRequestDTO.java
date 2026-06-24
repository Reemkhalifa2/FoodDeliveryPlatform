package com.example.FoodDeliveryPlatformDemo.dto.request;

import com.example.FoodDeliveryPlatformDemo.entities.Order;
import com.example.FoodDeliveryPlatformDemo.entities.OrderItem;
import com.example.FoodDeliveryPlatformDemo.utilities.HelperUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDTO {
    private CustomerRequestDTO customer;
    private RestaurantRequestDTO restaurant;
    private List<OrderItemRequestDTO> orderItems;
    private PaymentRequestDTO payment;
    private DeliveryRequestDTO delivery;

    public static Order toEntity(OrderRequestDTO dto) {
        Order order = new Order();
        if (HelperUtils.isNotNull(dto.getPayment())) {
            order.setPayment(
                    PaymentRequestDTO.toEntity(dto.getPayment())
            );
        }

        if (HelperUtils.isNotNull(dto.getPayment())) {
            order.setPayment(
                    PaymentRequestDTO.toEntity(dto.getPayment())
            );
        }

        if (HelperUtils.isNotNull(dto.getCustomer())) {
            order.setCustomer(
                    CustomerRequestDTO.toEntity(dto.getCustomer())
            );
        }

        if (HelperUtils.isNotNull(dto.getRestaurant())) {
            order.setRestaurant(
                    RestaurantRequestDTO.toEntity(dto.getRestaurant())
            );
        }

        if (HelperUtils.isNotNull(dto.getOrderItems())) {
            List<OrderItem> orderItems = new ArrayList<>();

            for (OrderItemRequestDTO itemDto : dto.getOrderItems()) {
                OrderItem item = OrderItemRequestDTO.toEntity(itemDto);
                orderItems.add(item);
            }
            order.setOrderItems(orderItems);
        }

        return order;
    }


}
