package com.example.FoodDeliveryPlatformDemo.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ComboMeal extends BaseEntity{
    private String comboName;
    private String description;
    private Double totalPrice;
    private Boolean isAvailable;

    @ManyToOne
    private Restaurant restaurant;

    @ManyToOne
    @JoinColumn(name = "menu_item_id")
    private MenuItem menuItem;
}
