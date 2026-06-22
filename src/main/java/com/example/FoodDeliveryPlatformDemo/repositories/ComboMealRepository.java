package com.example.FoodDeliveryPlatformDemo.repositories;

import com.example.FoodDeliveryPlatformDemo.entities.ComboMeal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ComboMealRepository extends JpaRepository<ComboMeal , Integer> {
    @Query("SELECT cm FROM ComboMeal cm JOIN cm.menuItem mi WHERE mi.id = :id")
    List<ComboMeal> findByMenuItemID(@Param("menuItemID") Integer id);

}
