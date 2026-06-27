package com.example.FoodDeliveryPlatformDemo.controllers;

import com.example.FoodDeliveryPlatformDemo.dto.request.ComboMealRequestDTO;
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

    @GetMapping("/{id}/analytics")
    public ResponseEntity<String> analytics(
            @PathVariable Integer id)
    {
        return ResponseEntity.ok(
                restaurantService.analytics(id)
        );
    }

    @GetMapping("{id}/search")
    public ResponseEntity<List<MenuItemResponseDTO>> searchMenuItems(
            @PathVariable Integer id,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Double minCalories,
            @RequestParam(required = false) Double maxCalories) {

        return ResponseEntity.ok(
                restaurantService.searchMenuItems(id,keyword, minCalories, maxCalories)
        );
    }
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
    public ResponseEntity<List<RestaurantResponseDTO>> getRestaurantsByCuisine(@PathVariable String cuisine){
        return ResponseEntity.ok(restaurantService.getRestaurantsByCuisine(cuisine));
    }

    @GetMapping("/delivery-fee/under/{maxFee}")
    public ResponseEntity<List<RestaurantResponseDTO>> getRestaurantsUnderDeliveryFee(
            @PathVariable Double maxFee) {

        return ResponseEntity.ok(restaurantService.getRestaurantsUnderDeliveryFee(maxFee));
    }

    @PutMapping("{id}/toggle-orders")
    public ResponseEntity<RestaurantResponseDTO> toggleAcceptingOrders(@PathVariable Integer id , @RequestParam Boolean status ){
        return ResponseEntity.ok(restaurantService.toggleAcceptingOrders(id , status));
    }
    @PutMapping("/{id}/fee/{newFee}")
    public ResponseEntity<RestaurantResponseDTO> updateDeliveryFee(
            @PathVariable Integer id,
            @PathVariable Double newFee) {

        return ResponseEntity.ok(
                restaurantService.updateDeliveryFee(id, newFee)
        );
    }

    @GetMapping("/{id}/menu")
    public ResponseEntity<List<MenuItemResponseDTO>> getMenuForRestaurant(@PathVariable Integer id ){
        return ResponseEntity.ok(restaurantService.getMenuForRestaurant(id));
    }

    @GetMapping("/{id}/combos")
    public ResponseEntity<List<ComboMealResponseDTO>> getAllComboMeal(@PathVariable Integer id){
        return ResponseEntity.ok(restaurantService.getAllComboMeal(id));
    }

    @PostMapping("/{id}/menu")
    public ResponseEntity<MenuItemResponseDTO> addMenuItem(@PathVariable Integer id , @Valid @RequestBody MenuItemRequestDTO dto){
        return ResponseEntity.ok(restaurantService.addMenuItem(id, dto));
    }

    @PutMapping("/menu/{itemId}/available")
    public ResponseEntity<MenuItemResponseDTO> updateAvailability(
            @PathVariable Integer itemId,
            @RequestParam Boolean status) {

        return ResponseEntity.ok(restaurantService.updateAvailability(itemId, status));
    }

    @PostMapping("/{id}/combos")
    public ResponseEntity<ComboMealResponseDTO> addComboMeal(
            @PathVariable Integer id,
            @Valid@RequestBody ComboMealRequestDTO dto) {
        return ResponseEntity.ok(restaurantService.addComboMeal(id, dto));
    }

    @PutMapping("/{id}/bulk-price-increase")
    public ResponseEntity<List<MenuItemResponseDTO>> bulkUpdateMenuItemPrices(
            @PathVariable Integer id,
            @RequestParam Double percentage) {

        return ResponseEntity.ok(restaurantService.bulkUpdateMenuItemPrices(id, percentage));
    }



}
