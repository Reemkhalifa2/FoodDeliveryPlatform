package com.example.FoodDeliveryPlatformDemo.dto.request;

import com.example.FoodDeliveryPlatformDemo.dto.response.CustomerResponseDTO;
import com.example.FoodDeliveryPlatformDemo.entities.MenuItem;
import com.example.FoodDeliveryPlatformDemo.entities.Order;
import com.example.FoodDeliveryPlatformDemo.entities.OrderItem;
import com.example.FoodDeliveryPlatformDemo.entities.Payment;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class OrderRequestDTO {
    private CustomerRequestDTO customer;
    private RestaurantRequestDTO restaurant;
    private List<OrderItemRequestDTO> orderItems;
    private PaymentRequestDTO payment;

    public static Order toEntity(OrderRequestDTO dto) {
        Order order = new Order();
        if (dto.getPayment() != null) {
            order.setPayment(
                    PaymentRequestDTO.toEntity(dto.getPayment())
            );
        }

        if (dto.getCustomer() != null) {
            order.setCustomer(
                    CustomerRequestDTO.toEntity(dto.getCustomer())
            );
        }

        if (dto.getRestaurant() != null) {
            order.setRestaurant(
                    RestaurantRequestDTO.toEntity(dto.getRestaurant())
            );
        }

        if (dto.getOrderItems() != null) {
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
