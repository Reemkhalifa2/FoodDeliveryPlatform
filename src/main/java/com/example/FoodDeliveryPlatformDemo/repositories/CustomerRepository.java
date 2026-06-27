package com.example.FoodDeliveryPlatformDemo.repositories;

import com.example.FoodDeliveryPlatformDemo.entities.Customer;
import com.example.FoodDeliveryPlatformDemo.entities.CustomerAddress;
import com.example.FoodDeliveryPlatformDemo.entities.Order;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
@Repository

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    @Query("select c from Customer c where lower(c.firstName) like lower(concat('%', :name, '%'))")
    Page<Customer> search(@Param("name") String name, Pageable pageable);

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
    @Query("SELECT c.orders FROM Customer c WHERE c.id = :customerId")
    List<Order> findOrdersByCustomerId(@Param("customerId") Integer customerId);

    @Query("SELECT c FROM Customer c  WHERE c.isActive = true ORDER BY c.loyaltyPoints DESC LIMIT 10")
    List<Customer> findTop10ByOrderByLoyaltyPointsDesc();

}
