package com.example.FoodDeliveryPlatformDemo.repositories;

import com.example.FoodDeliveryPlatformDemo.entities.Restaurant;
import com.example.FoodDeliveryPlatformDemo.entities.RestaurantOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantOwnerRepository extends JpaRepository<RestaurantOwner, Integer> {

}
