package com.example.FoodDeliveryPlatformDemo.dto.request;

import com.example.FoodDeliveryPlatformDemo.entities.Customer;
import com.example.FoodDeliveryPlatformDemo.entities.RestaurantOwner;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RestaurantOwnerRequestDTO extends PersonDTO {
    private String businessLicenseCode;

    public static RestaurantOwner toEntity(RestaurantOwnerRequestDTO dto) {
        RestaurantOwner restaurantOwner = new RestaurantOwner();
        restaurantOwner.setFirstName(dto.getFirstName());
        restaurantOwner.setLastName(dto.getLastName());
        restaurantOwner.setEmail(dto.getEmail());
        restaurantOwner.setPhone(dto.getPhone());
        restaurantOwner.setPasswordHash(dto.getPassword());
        restaurantOwner.setBusinessLicenseCode(dto.getBusinessLicenseCode());
        return restaurantOwner;
    }

}
