package com.example.FoodDeliveryPlatformDemo.repositories;

import com.example.FoodDeliveryPlatformDemo.entities.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment , Integer> {
    @Query("SELECT p FROM Payment p WHERE p.id =:id AND p.isActive = true")
    Payment getById(@Param("id") Integer id);
    @Query("""
        SELECT p
        FROM Payment p
        WHERE 
        p.isActive=true
        AND p.paymentMethod = :paymentMethod
        AND p.status = :status
        AND p.createdDate >= :from
        AND p.createdDate <= :to
    """)
    Page<Payment> filterPayments(
            @Param("paymentMethod") String paymentMethod,
            @Param("status") String status,
            @Param("from") Date from,
            @Param("to") Date to,
            Pageable pageable
    );
    @Query("""
    SELECT p.paymentMethod, SUM(p.amount)
    FROM Payment p
    GROUP BY p.paymentMethod
""")
    List<Object[]> getTotalAmountGroupedByMethod();



}
