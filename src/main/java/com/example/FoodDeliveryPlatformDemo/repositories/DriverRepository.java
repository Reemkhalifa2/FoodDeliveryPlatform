package com.example.FoodDeliveryPlatformDemo.repositories;

import com.example.FoodDeliveryPlatformDemo.entities.Delivery;
import com.example.FoodDeliveryPlatformDemo.entities.DeliveryDriver;
import com.example.FoodDeliveryPlatformDemo.utilities.HelperUtils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public interface DriverRepository extends JpaRepository<DeliveryDriver , Integer> {

    @Query("SElECT d FROM DeliveryDriver d WHERE d.isActive = true AND d.id=:id")
    DeliveryDriver getById(@Param("id") Integer id);

    @Query("SElECT d FROM DeliveryDriver d WHERE d.isActive = true ")
    List<DeliveryDriver> getAll();

    @Query("SElECT d FROM DeliveryDriver d WHERE d.isActive = true AND d.isOnline = true ")
    List<DeliveryDriver> getByIsOnline();

    @Query("SELECT d FROM DeliveryDriver d WHERE d.isActive = true AND d.isOnline = true ORDER BY d.id ASC LIMIT 1")
    DeliveryDriver findFirstOnline();

    @Query("SELECT d FROM DeliveryDriver d JOIN d.deliveries del WHERE del.status = 'DELIVERED' AND d.isActive = true GROUP BY d.id ORDER BY COUNT(del) DESC")
    List<DeliveryDriver> findTopDriversByCompletedDeliveries();

    boolean existsByEmail(String email);

    @Query("""
        SELECT d FROM DeliveryDriver d
        WHERE d.isOnline = true
          AND (6371 * acos(
                cos(radians(:lat)) * cos(radians(d.currentLat)) *
                cos(radians(d.currentLng) - radians(:lng)) +
                sin(radians(:lat)) * sin(radians(d.currentLat))
              )) <= :radiusKm
        """)
    List<DeliveryDriver> findNearbyOnlineDrivers(
            @Param("lat") Double lat,
            @Param("lng") Double lng,
            @Param("radiusKm") Double radiusKm
    );











}
