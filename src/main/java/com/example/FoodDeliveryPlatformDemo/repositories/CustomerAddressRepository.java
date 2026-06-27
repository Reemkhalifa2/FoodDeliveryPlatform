package com.example.FoodDeliveryPlatformDemo.repositories;

import com.example.FoodDeliveryPlatformDemo.entities.Customer;
import com.example.FoodDeliveryPlatformDemo.entities.CustomerAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository

public interface CustomerAddressRepository extends JpaRepository<CustomerAddress , Integer> {
    @Query("SELECT ca.customer FROM CustomerAddress ca WHERE ca.city = :city AND ca.isActive=true")
    List<Customer>findByCity(@Param("city") String city);

    @Query("SELECT ca FROM CustomerAddress ca WHERE ca.isActive = true AND ca.id=:id")
    CustomerAddress getById(@Param("id") Integer id);

    @Query("SELECT ca FROM CustomerAddress ca WHERE ca.isActive = true AND ca.customer.id=:id AND ca.isDefault = true ")
    CustomerAddress getDefaultAddress(@Param("id") Integer id);




}
