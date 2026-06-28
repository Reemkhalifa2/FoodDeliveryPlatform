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
    List<MenuItem> findByRestaurantId(@Param("id") Integer id);
    @Query("""
SELECT m FROM MenuItem m
WHERE m.restaurant.id = :id
AND (:name IS NULL OR LOWER(m.name) LIKE LOWER(CONCAT('%', :name, '%')))
AND (:minCalories IS NULL OR m.calories >= :minCalories)
AND (:maxCalories IS NULL OR m.calories <= :maxCalories)
""")
    List<MenuItem> searchMenuItems(
            @Param("id") Integer id,
            @Param("name") String name,
            @Param("minCalories") Double minCalories,
            @Param("maxCalories") Double maxCalories
    );
    @Query("SELECT m FROM MenuItem m WHERE m.isActive = true AND m.restaurant.id=:id AND m.isAvailable = true")
    MenuItem findByRestaurantIdAndIsAvailableTrue(@Param("id") Integer id);

    @Query("SELECT m FROM MenuItem m WHERE m.isActive = true AND m.isVegetarian = true ")
    List<MenuItem> findByIsVegetarianTrue();

    @Query("SELECT m FROM MenuItem m WHERE m.isActive = true AND m.price >:min AND m.price < :max ")
    List<MenuItem>  findByPriceBetween(@Param("min") double min,@Param("max") double max);





}
