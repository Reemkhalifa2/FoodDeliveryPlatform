package com.example.FoodDeliveryPlatformDemo.repositories;

import com.example.FoodDeliveryPlatformDemo.entities.Delivery;
import com.example.FoodDeliveryPlatformDemo.entities.DeliveryDriver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository

public interface DeliveryRepository extends JpaRepository<Delivery , Integer> {
    @Query("SELECT d FROM Delivery d WHERE d.id = :id AND d.isActive = true")
    Delivery getByID(@Param("id") Integer id);

    @Query("SELECT d FROM Delivery d WHERE d.deliveryDriver.id = :driverId AND d.isActive = true")
    List<Delivery> getActiveDelivery(@Param("driverId") Integer driverId);

    @Query("SELECT d FROM Delivery d WHERE d.deliveryDriver.id = :driverId AND d.status = :status AND d.isActive = true")
    List<Delivery> getDeliveries(@Param("driverId") Integer driverId, @Param("status") String status);
    @Query("SELECT d FROM Delivery d WHERE  d.status = :status AND d.isActive = true")
    List<Delivery> getDeliveries(@Param("status") String status);
}
