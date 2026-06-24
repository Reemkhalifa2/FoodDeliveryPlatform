package com.example.FoodDeliveryPlatformDemo.dto.response;

import com.example.FoodDeliveryPlatformDemo.dto.summary.RestaurantOwnerSummaryDTO;
import com.example.FoodDeliveryPlatformDemo.dto.summary.RestaurantSummaryDTO;
import com.example.FoodDeliveryPlatformDemo.entities.Customer;
import com.example.FoodDeliveryPlatformDemo.entities.RestaurantOwner;
import com.example.FoodDeliveryPlatformDemo.utilities.HelperUtils;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class RestaurantOwnerResponseDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private List<RestaurantSummaryDTO> restaurants;

    public static RestaurantOwnerResponseDTO toResponse(RestaurantOwner restaurantOwner) {
        RestaurantOwnerResponseDTO dto = new RestaurantOwnerResponseDTO();
        dto.setFirstName(restaurantOwner.getFirstName());
        dto.setLastName(restaurantOwner.getLastName());
        dto.setEmail(restaurantOwner.getEmail());
        dto.setPhone(restaurantOwner.getPhone());
        if(HelperUtils.isNotNull(restaurantOwner.getRestaurants())){
            dto.setRestaurants(
                    RestaurantSummaryDTO.toSummary(restaurantOwner.getRestaurants())
            );
        }
        return dto;
    }

    public static List<RestaurantOwnerResponseDTO> toResponse(List<RestaurantOwner> restaurantOwners) {
        List<RestaurantOwnerResponseDTO> dtos = new ArrayList<>();
        for (RestaurantOwner entity : restaurantOwners) {
            dtos.add(toResponse(entity));
        }
        return dtos;
    }


}
