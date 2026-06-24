package com.example.FoodDeliveryPlatformDemo.services;

import com.example.FoodDeliveryPlatformDemo.dto.request.RestaurantOwnerRequestDTO;
import com.example.FoodDeliveryPlatformDemo.dto.response.RestaurantOwnerResponseDTO;
import com.example.FoodDeliveryPlatformDemo.entities.RestaurantOwner;
import com.example.FoodDeliveryPlatformDemo.exceptions.NullRequestBodyException;
import com.example.FoodDeliveryPlatformDemo.repositories.RestaurantOwnerRepository;
import com.example.FoodDeliveryPlatformDemo.repositories.RestaurantRepository;
import com.example.FoodDeliveryPlatformDemo.utilities.HelperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class RestaurantService {

    @Autowired
    public RestaurantService(RestaurantOwnerRepository restaurantOwnerRepository, RestaurantRepository restaurantRepository) {
        this.restaurantOwnerRepository = restaurantOwnerRepository;
        this.restaurantRepository = restaurantRepository;
    }

    RestaurantRepository restaurantRepository;
    RestaurantOwnerRepository restaurantOwnerRepository;


    public RestaurantOwnerResponseDTO addRestaurantOwner(RestaurantOwnerRequestDTO restaurantOwnerRequestDTO){
        if(HelperUtils.isNull(restaurantOwnerRequestDTO)){
            throw new NullRequestBodyException("Request body is null");
        }
        RestaurantOwner restaurantOwner = restaurantOwnerRepository.save(RestaurantOwnerRequestDTO.toEntity(restaurantOwnerRequestDTO));
        restaurantOwner.setIsActive(true);
        restaurantOwner.setCreatedDate(new Date());
        return RestaurantOwnerResponseDTO.toResponse(restaurantOwner);
    }
}
