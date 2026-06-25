package com.example.FoodDeliveryPlatformDemo.repositories;

import com.example.FoodDeliveryPlatformDemo.entities.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository

public interface MenuItemRepository extends JpaRepository<MenuItem, Integer> {
    @Query("SELECT m FROM MenuItem m WHERE m.isActive = true AND m.id=:id")
    MenuItem getById(@Param("id") Integer id);

    @Query("SELECT m FROM MenuItem m WHERE m.isActive = true AND m.restaurant.id=:id")
    MenuItem findByRestaurantId(@Param("id") Integer id);

    @Query("SELECT m FROM MenuItem m WHERE m.isActive = true AND m.restaurant.id=:id AND m.isAvailable = true")
    MenuItem findByRestaurantIdAndIsAvailableTrue(@Param("id") Integer id);

    @Query("SELECT m FROM MenuItem m WHERE m.isActive = true AND m.isVegetarian = true ")
    List<MenuItem> findByIsVegetarianTrue();

    @Query("SELECT m FROM MenuItem m WHERE m.isActive = true AND m.price >:min AND m.price < :max ")
    List<MenuItem>  findByPriceBetween(@Param("min") double min,@Param("max") double max);





}
