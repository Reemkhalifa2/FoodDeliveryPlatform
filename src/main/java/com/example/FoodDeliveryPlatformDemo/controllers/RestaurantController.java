package com.example.FoodDeliveryPlatformDemo.controllers;

import com.example.FoodDeliveryPlatformDemo.dto.request.RestaurantOwnerRequestDTO;
import com.example.FoodDeliveryPlatformDemo.dto.request.RestaurantRequestDTO;
import com.example.FoodDeliveryPlatformDemo.dto.response.RestaurantOwnerResponseDTO;
import com.example.FoodDeliveryPlatformDemo.dto.response.RestaurantResponseDTO;
import com.example.FoodDeliveryPlatformDemo.entities.Restaurant;
import com.example.FoodDeliveryPlatformDemo.services.RestaurantService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("restaurants")
public class RestaurantController {
    @Autowired
    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }
    RestaurantService restaurantService;

    @PostMapping("restaurantOwner")
    public RestaurantOwnerResponseDTO addRestaurantOwner(@Valid @RequestBody RestaurantOwnerRequestDTO restaurantOwnerRequestDTO){
        return restaurantService.addRestaurantOwner(restaurantOwnerRequestDTO);
    }

    @PostMapping("/owner/{ownerId}")
    public RestaurantResponseDTO createRestaurant(@Valid @RequestBody RestaurantRequestDTO dto , @PathVariable Integer ownerId){
        return restaurantService.createRestaurant(dto , ownerId);
    }

}
