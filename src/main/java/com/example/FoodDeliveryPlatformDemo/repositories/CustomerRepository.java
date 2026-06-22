package com.example.FoodDeliveryPlatformDemo.repositories;

import com.example.FoodDeliveryPlatformDemo.entities.Customer;
import lombok.Data;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    @Query("SELECT C FROM Customer C WHERE C.email=:email AND C.isActive=true")
    Customer findByEmail(@Param("email") String email);

    @Query("SELECT C from Customer C WHERE C.loyaltyPoints=:loyaltyPoints AND C.isActive=true")
    List<Customer>findByLoyaltyPointsGreaterThanEqual(@Param("loyaltyPoints") Integer loyaltyPoints);
    @Query("SELECT c FROM Customer c WHERE c.createdDate BETWEEN :start AND :end")
    List<Customer> findCustomersByDateRange(
            @Param("start") Date start,
            @Param("end") Date end
    );
}
