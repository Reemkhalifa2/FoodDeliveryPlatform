package com.example.FoodDeliveryPlatformDemo.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PersonDTO {
    @NotBlank(message = "First name cannot be blank.")
    private String firstName;
    @NotBlank(message = "Last name cannot be blank.")
    private String lastName;
    @Column(unique = true)
    @NotBlank(message = "Email cannot be blank.")
    @Email(message = "Invalid email")
    private String email;
    @NotBlank(message = "Phone number cannot be blank.")
    @Pattern(
            regexp = "^[79][0-9]{7}$",
            message = "Phone number must be 8 digits and start with 7 or 9"
    )    private String phone;
    @NotBlank(message = "Password cannot be blank.")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;
}
