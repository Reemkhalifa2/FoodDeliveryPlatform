package com.example.FoodDeliveryPlatformDemo.entities;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@MappedSuperclass
@Data
public class Person extends BaseEntity{
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String email;
    private String phone;
    private String passwordHash;
}
