package com.example.FoodDeliveryPlatformDemo.repositories;

import com.example.FoodDeliveryPlatformDemo.entities.Order;
import com.example.FoodDeliveryPlatformDemo.entities.Restaurant;
import com.example.FoodDeliveryPlatformDemo.enums.OrderStatus;
import jdk.dynalink.linker.LinkerServices;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
@Repository

public interface OrderRepository extends JpaRepository<Order, Integer> {
    @Query("SELECT o FROM Order o WHERE o.isActive = true AND o.customer.id = :customerId")
    List<Order> findByCustomerId(@Param("customerId") Integer customerId);

    @Query("SELECT o FROM Order o WHERE o.isActive = true AND o.restaurant.id = :restaurantId")
    List<Order> findByRestaurantId(@Param("restaurantId") Integer restaurantId);

    @Query("SELECT o FROM Order o WHERE o.isActive = true AND o.restaurant.id = :restaurantId  AND  o.createdDate BETWEEN :start AND :end")
    List<Order>findByOrderDateBetween(@Param("restaurantId") Integer restaurantId , @Param("start") Date start, @Param("end") Date end);

    @Query("SELECT o FROM Order o WHERE o.isActive = true AND o.delivery.id = :driverId AND o.status = :status")
    List<Order>findByDeliveryDriverIdAndStatus(@Param("driverId") Integer driverId, @Param("status") String status);

    @Query("SELECT COUNT(o) FROM Order o WHERE o.isActive = true AND o.restaurant.id = :restaurantId AND o.status = 'COMPLETED'")
    Integer countCompletedOrders(@Param("restaurantId") Integer restaurantId);

    @Query("SELECT SUM(o.totalAmount) FROM Order o WHERE o.isActive = true AND o.status ='DELIVERED' AND o.delivery.assignedAt = :date ")
    Double sumDeliveredOrdersByDate(@Param("date") Date date);

    @Query("SELECT  o FROM Order o WHERE o.isActive = true AND o.delivery.assignedAt = :date ")
    List<Order> OrdersByDate(@Param("date") Date date);

    @Query("SELECT o FROM Order o WHERE o.isActive = true AND o.id=:id")
    Order getById(@Param("id") Integer id);

    @Query("""
        SELECT o FROM Order o
        WHERE o.customer.Id = :id
          AND o.status = :status
          AND  o.createdDate BETWEEN :fromDate AND :toDate
    """)
    Page<Order> findByFilters(
            @Param("id")       String    id,
            @Param("status") OrderStatus status,
            @Param("fromDate") Date fromDate,
            @Param("toDate")   Date toDate,
            Pageable pageable
    );







}
