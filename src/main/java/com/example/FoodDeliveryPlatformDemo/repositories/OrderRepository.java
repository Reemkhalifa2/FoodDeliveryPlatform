package com.example.FoodDeliveryPlatformDemo.repositories;

import com.example.FoodDeliveryPlatformDemo.entities.Order;
import jdk.dynalink.linker.LinkerServices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
@Repository

public interface OrderRepository extends JpaRepository<Order, Integer> {
    @Query("SELECT o FROM Order o WHERE o.isActive = true AND o.customer.id = :customerId")
    List<Order> findByCustomerId(@Param("customerId") Integer customerId);

    @Query("SELECT o FROM Order o WHERE o.isActive = true AND o.restaurant.id = :restaurantId and o.status = status")
    List<Order> findByRestaurantIdAndStatus(@Param("restaurantId") Integer restaurantId, String status);

    @Query("SELECT o FROM Order o WHERE o.isActive = true AND  o.createdDate BETWEEN :start AND :end")
    List<Order>findByOrderDateBetween(@Param("start") Date start, @Param("end") Date end);

    @Query("SELECT o FROM Order o WHERE o.isActive = true AND o.delivery.id = :driverId AND o.status = :status")
    List<Order>findByDeliveryDriverIdAndStatus(@Param("driverId") Integer driverId, @Param("status") String status);

    @Query("SELECT COUNT(o) FROM Order o WHERE o.isActive = true AND o.restaurant.id = :restaurantId AND o.status = 'COMPLETED'")
    Integer countCompletedOrders(@Param("restaurantId") Integer restaurantId);

    @Query("SELECT SUM(o.totalAmount) FROM Order o WHERE o.isActive = true AND o.status ='DELIVERED' AND o.delivery.assignedAt = :date ")
    Double sumDeliveredOrdersByDate(@Param("date") Date date);






}
