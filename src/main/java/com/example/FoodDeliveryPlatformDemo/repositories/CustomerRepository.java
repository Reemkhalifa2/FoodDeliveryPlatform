package com.example.FoodDeliveryPlatformDemo.repositories;

import com.example.FoodDeliveryPlatformDemo.entities.Customer;
import com.example.FoodDeliveryPlatformDemo.entities.CustomerAddress;
import lombok.Data;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    @Query("SELECT c FROM Customer c WHERE c.isActive=true AND c.email=:email ")
    Customer findByEmail(@Param("email") String email);
    @Query("SELECT c FROM Customer c WHERE c.isActive=true AND c.id=:id ")
    Customer findByID(@Param("id") Integer id);

    @Query("SELECT c from Customer c WHERE c.isActive = true AND c.loyaltyPoints=:loyaltyPoints  ")
    List<Customer>findByLoyaltyPointsGreaterThanEqual(@Param("loyaltyPoints") Integer loyaltyPoints);
    @Query("SELECT c FROM Customer c WHERE  c.isActive = true AND c.createdDate BETWEEN :start AND :end")
    List<Customer> findCustomersByDateRange(
            @Param("start") Date start,
            @Param("end") Date end
    );
    @Query("SELECT c FROM Customer c WHERE c.isActive=true ")
    List<Customer> findAll();

    @Query("SELECT c.customerAddresses FROM Customer c WHERE c.id = :customerId")
    List<CustomerAddress> findAddressesByCustomerId(@Param("customerId") Integer customerId);

}
