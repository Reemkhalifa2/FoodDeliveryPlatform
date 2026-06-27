package com.example.FoodDeliveryPlatformDemo.entities;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@MappedSuperclass
@Data
public class Person extends BaseEntity{
    private String firstName;
    private String lastName;
    @Column(nullable = false, unique = true)
    private String email;
    private String phone;
    private String passwordHash;

}
