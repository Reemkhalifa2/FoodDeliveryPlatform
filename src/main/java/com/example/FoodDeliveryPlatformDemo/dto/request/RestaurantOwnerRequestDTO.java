package com.example.FoodDeliveryPlatformDemo.dto.request;

import com.example.FoodDeliveryPlatformDemo.entities.Customer;
import com.example.FoodDeliveryPlatformDemo.entities.RestaurantOwner;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantOwnerRequestDTO extends PersonDTO {
    @NotBlank
    private String businessLicenseCode;

    public static RestaurantOwner toEntity(RestaurantOwnerRequestDTO dto) {
        RestaurantOwner restaurantOwner = new RestaurantOwner();
        restaurantOwner.setFirstName(dto.getFirstName());
        restaurantOwner.setLastName(dto.getLastName());
        restaurantOwner.setEmail(dto.getEmail());
        restaurantOwner.setPhone(dto.getPhone());
        restaurantOwner.setPasswordHash(dto.getPassword());
        restaurantOwner.setBusinessLicenseCode(dto.getBusinessLicenseCode());
        restaurantOwner.setUpdatedDate(new Date());
        return restaurantOwner;
    }

}
