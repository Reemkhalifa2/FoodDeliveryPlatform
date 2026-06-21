package com.example.FoodDeliveryPlatformDemo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Driver;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@AllArgsConstructor
public class DeliveryDriver extends Person{

    private String driverCode;
    private String vehicleType;
    private String vehiclePlate;
    private Double currentLat; //Current latitude location of driver
    private Double currentLng; //Current longitude location of driver
    private Boolean isOnline;
    @OneToMany
    private List<Delivery> deliveries;

}
