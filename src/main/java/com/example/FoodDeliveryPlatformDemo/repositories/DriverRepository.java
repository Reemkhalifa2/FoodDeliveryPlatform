package com.example.FoodDeliveryPlatformDemo.repositories;

import com.example.FoodDeliveryPlatformDemo.entities.Delivery;
import com.example.FoodDeliveryPlatformDemo.entities.DeliveryDriver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DriverRepository extends JpaRepository<DeliveryDriver , Integer> {

    @Query("SElECT d FROM DeliveryDriver d WHERE d.isActive = true AND d.id=:id")
    DeliveryDriver getById(@Param("id") Integer id);

    @Query("SElECT d FROM DeliveryDriver d WHERE d.isActive = true ")
    List<DeliveryDriver> getAll();

    @Query("SElECT d FROM DeliveryDriver d WHERE d.isActive = true AND d.isOnline = true ")
    List<DeliveryDriver> getByIsOnline();

    @Query("SELECT d FROM DeliveryDriver d WHERE d.isActive = true AND d.isOnline = true ORDER BY d.id ASC")
    DeliveryDriver findFirstOnline();






}
