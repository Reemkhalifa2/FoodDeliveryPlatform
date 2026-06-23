package com.example.FoodDeliveryPlatformDemo.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RestaurantOwnerResponseDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;


}
