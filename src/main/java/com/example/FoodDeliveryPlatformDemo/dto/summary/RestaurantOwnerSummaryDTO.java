package com.example.FoodDeliveryPlatformDemo.dto.summary;

import com.example.FoodDeliveryPlatformDemo.entities.Customer;
import com.example.FoodDeliveryPlatformDemo.entities.RestaurantOwner;
import lombok.Data;

@Data
public class RestaurantOwnerSummaryDTO {
    private String fullName;

    public static RestaurantOwnerSummaryDTO toSummary( RestaurantOwner restaurantOwner) {
        RestaurantOwnerSummaryDTO dto = new RestaurantOwnerSummaryDTO();
        dto.setFullName(restaurantOwner.getFirstName()
                + " "
                + restaurantOwner.getLastName()
        );
        return dto;
    }


}
