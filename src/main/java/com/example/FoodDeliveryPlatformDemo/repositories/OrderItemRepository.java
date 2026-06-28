package com.example.FoodDeliveryPlatformDemo.repositories;

import com.example.FoodDeliveryPlatformDemo.entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem , Integer> {
    @Query("SELECT oi FROM OrderItem oi WHERE oi.isActive =true AND oi.id=:id")
    OrderItem getById(@Param("id") Integer id);

}
