package com.example.FoodDeliveryPlatformDemo.repositories;

import com.example.FoodDeliveryPlatformDemo.entities.Review;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review , Integer> {
    @Query("SELECT r FROM Review r WHERE r.isActive = true AND r.restaurant.id = :restaurantId")
    List<Review> findByRestaurantId(@Param("restaurantId") Integer restaurantId);

    @Query("SELECT r FROM Review r WHERE r.isActive = true AND r.deliveryDriver.id = :driverId")
    List<Review> findByDriverId(@Param("driverId") Integer driverId);

    @Query("SELECT r FROM Review r WHERE r.isActive = true AND r.id = :id")
    Review getById(@Param("id") Integer id);


}
