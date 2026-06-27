package com.example.FoodDeliveryPlatformDemo.repositories;

import com.example.FoodDeliveryPlatformDemo.entities.OrderStatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StatusHistoryRepository extends JpaRepository<OrderStatusHistory , Integer> {
    @Query("SELECT o FROM OrderStatusHistory o WHERE o.order.id=:id")
    List<OrderStatusHistory> findByOrderId(@Param("id") Integer orderId);
}
