package com.example.FoodDeliveryPlatformDemo.repositories;

import com.example.FoodDeliveryPlatformDemo.entities.Restaurant;
import com.example.FoodDeliveryPlatformDemo.entities.RestaurantOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantOwnerRepository extends JpaRepository<RestaurantOwner, Integer> {
    @Query("SELECT o FROM RestaurantOwner o WHERE o.isActive = true AND o.id=:id")
    RestaurantOwner getById(@Param("id") Integer id);
}
