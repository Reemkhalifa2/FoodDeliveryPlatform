package com.example.FoodDeliveryPlatformDemo.entities;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ComboMeal {
    private String comboName;
    private String description;
    private Double totalPrice;
    private Boolean isAvailable;
}
