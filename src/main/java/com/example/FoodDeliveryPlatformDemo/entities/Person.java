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
    @NotBlank(message = "FIRST NANE CANNOT BE BLANK.")
    private String firstName;
    @NotBlank(message = "LAST NANE CANNOT BE BLANK.")
    @Column(unique = true)
    @NotBlank(message = "EMAIL CANNOT BE BLANK.")
    @Email(message = "INVALID EMAIL")
    private String email;
    @NotBlank(message = "PHONE NUMBER CANNOT BE BLANK.")
    @Pattern(
            regexp = "^[79][0-9]{7}$",
            message = "Phone number must be 8 digits and start with 7 or 9"
    )    private String phone;
    @NotBlank(message = "PASSWORD CANNOT BE BLANK")
    private String passwordHash;
}
