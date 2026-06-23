package com.example.FoodDeliveryPlatformDemo.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RestaurantOwnerRequestDTO extends PersonDTO {
    private String businessLicenseCode;

}
