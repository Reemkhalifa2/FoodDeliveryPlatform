package com.example.FoodDeliveryPlatformDemo.dto;

import com.example.FoodDeliveryPlatformDemo.enums.OrderStatus;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class OrderEtaResponseDTO {

    private Integer orderId;

    private Integer driverId;

    private Double distanceKm;

    private Integer estimatedMinutes;

    private OrderStatus status;

}