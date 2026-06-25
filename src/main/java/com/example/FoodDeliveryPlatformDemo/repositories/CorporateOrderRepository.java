package com.example.FoodDeliveryPlatformDemo.repositories;

import com.example.FoodDeliveryPlatformDemo.entities.CorporateOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CorporateOrderRepository extends JpaRepository<CorporateOrder , Integer> {

}
