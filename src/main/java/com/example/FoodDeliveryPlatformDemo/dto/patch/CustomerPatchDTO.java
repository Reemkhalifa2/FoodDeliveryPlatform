package com.example.FoodDeliveryPlatformDemo.dto.patch;

import com.example.FoodDeliveryPlatformDemo.dto.request.CustomerAddressRequestDTO;
import com.example.FoodDeliveryPlatformDemo.entities.CustomerAddress;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerPatchDTO {
    private String firstName;
    private String lastName;
    @Email(message = "Invalid email")
    private String email;
    @Pattern(
            regexp = "^[79][0-9]{7}$",
            message = "Phone number must be 8 digits and start with 7 or 9"
    )
    private String phone;
    private CustomerAddressRequestDTO customerAddress;

}
