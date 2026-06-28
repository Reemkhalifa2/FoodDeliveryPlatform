package com.example.FoodDeliveryPlatformDemo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Restaurant extends BaseEntity{
    private String name;
    private String description;
    private String cuisineType;
    private Time openingTime;
    private Time closingTime;
    private Integer minOrderAmount;
    private Double deliveryFee;
    private Boolean acceptingOrders;
    private Double latitude;
    private Double longitude;

    @ManyToOne
    RestaurantOwner restaurantOwner;
    @OneToMany
    List<MenuItem> menuItems;
    @OneToMany
    private List<ComboMeal> comboMeals;

}
