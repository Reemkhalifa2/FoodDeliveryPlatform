package com.example.FoodDeliveryPlatformDemo.controllers;

import com.example.FoodDeliveryPlatformDemo.dto.request.MenuItemRequestDTO;
import com.example.FoodDeliveryPlatformDemo.dto.request.RestaurantOwnerRequestDTO;
import com.example.FoodDeliveryPlatformDemo.dto.request.RestaurantRequestDTO;
import com.example.FoodDeliveryPlatformDemo.dto.response.ComboMealResponseDTO;
import com.example.FoodDeliveryPlatformDemo.dto.response.MenuItemResponseDTO;
import com.example.FoodDeliveryPlatformDemo.dto.response.RestaurantOwnerResponseDTO;
import com.example.FoodDeliveryPlatformDemo.dto.response.RestaurantResponseDTO;
import com.example.FoodDeliveryPlatformDemo.entities.Restaurant;
import com.example.FoodDeliveryPlatformDemo.services.RestaurantService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("restaurants")
public class RestaurantController {
    @Autowired
    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }
    RestaurantService restaurantService;

    @PostMapping("restaurantOwner")
    public ResponseEntity<RestaurantOwnerResponseDTO> addRestaurantOwner(@Valid @RequestBody RestaurantOwnerRequestDTO restaurantOwnerRequestDTO){
        return ResponseEntity.ok(restaurantService.addRestaurantOwner(restaurantOwnerRequestDTO));
    }

    @PostMapping("/owner/{ownerId}")
    public ResponseEntity<RestaurantResponseDTO> createRestaurant(@Valid @RequestBody RestaurantRequestDTO dto , @PathVariable Integer ownerId){
        return ResponseEntity.ok(restaurantService.createRestaurant(dto , ownerId));
    }

    @GetMapping
    public ResponseEntity<List<RestaurantResponseDTO>> getAllRestaurants(){
        return ResponseEntity.ok(restaurantService.getAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<RestaurantResponseDTO> getRestaurantById(@PathVariable Integer id){
        return ResponseEntity.ok(restaurantService.getById(id));
    }

    @GetMapping("/cuisine/{cuisine}")
    public ResponseEntity<List<RestaurantResponseDTO>> getByCuisine(@PathVariable String cuisine){
        return ResponseEntity.ok(restaurantService.getByCuisine(cuisine));
    }

    @PutMapping("{id}/toggle-orders")
    public ResponseEntity<RestaurantResponseDTO> acceptOrder(@PathVariable Integer id , @RequestParam Boolean accepting ){
        return ResponseEntity.ok(restaurantService.acceptOrder(id , accepting));
    }

    @GetMapping("/{id}/menu")
    public ResponseEntity<List<MenuItemResponseDTO>> getAllMenuItems(@PathVariable Integer id ){
        return ResponseEntity.ok(restaurantService.getAllMenuItems(id));
    }

    @GetMapping("/{id}/combos")
    public ResponseEntity<List<ComboMealResponseDTO>> getAllComboMeal(@PathVariable Integer id){
        return ResponseEntity.ok(restaurantService.getAllComboMeal(id));
    }

    @PostMapping("/{id}/menu")
    public ResponseEntity<MenuItemResponseDTO> addMenuItem(@PathVariable Integer id , @Valid @RequestBody MenuItemRequestDTO dto){
        return ResponseEntity.ok(restaurantService.addMenuItem(id, dto));
    }



}
