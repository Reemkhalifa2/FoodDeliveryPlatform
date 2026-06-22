package com.example.FoodDeliveryPlatformDemo.repositories;

import com.example.FoodDeliveryPlatformDemo.entities.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {

    @Query("SELECT r FROM Restaurant r where r.isActive = true AND r.cuisineType =:cuisineType")
    List<Restaurant>findByCuisineTypeIgnoreCase(String cuisineType);

    @Query("SELECT r FROM Restaurant r where r.isActive = true AND r.acceptingOrders = true")
    List<Restaurant>findByAcceptingOrdersTrue();

    @Query("SELECT r FROM Restaurant r WHERE r.isActive = true AND r.deliveryFee <= :fee")
    List<Restaurant>findByDeliveryFeeLessThanEqual(@Param("fee") double fee);

    @Query("SELECT r FROM Restaurant r WHERE r.isActive = true AND r.restaurantOwner.id = :ownerId")
    List<Restaurant>findByOwner(@Param("ownerId") Long ownerId);

    @Query("SELECT r FROM Restaurant r WHERE r.name LIKE %:keyword%")
    List<Restaurant> findByNameContainingIgnoreCase(String keyword);





}
