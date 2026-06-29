package com.example.FoodDeliveryPlatformDemo.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DriverPerformanceDTO {
    private Integer driverId;
    private Integer completedDeliveries;
    private Double avgDeliveryTimeMinutes;
}