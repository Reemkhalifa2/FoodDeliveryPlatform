package com.example.FoodDeliveryPlatformDemo.repositories;

import com.example.FoodDeliveryPlatformDemo.entities.Delivery;
import com.example.FoodDeliveryPlatformDemo.entities.DeliveryDriver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository

public interface DeliveryRepository extends JpaRepository<Delivery , Integer> {
    @Query("SELECT d FROM Delivery d WHERE d.id = :id AND d.isActive = true")
    Delivery getByID(@Param("id") Integer id);

    @Query("SELECT d FROM Delivery d WHERE d.deliveryDriver.id = :driverId AND d.isActive = true")
    List<Delivery> getActiveDelivery(@Param("driverId") Integer driverId);

    @Query("SELECT d FROM Delivery d WHERE d.deliveryDriver.id = :driverId AND d.status = :status AND d.isActive = true")
    List<Delivery> getDeliveries(@Param("driverId") Integer driverId, @Param("status") String status);
    @Query("SELECT d FROM Delivery d WHERE  d.status = :status AND d.isActive = true")
    List<Delivery> getDeliveries(@Param("status") String status);


    @Query(value = """
            SELECT COUNT(*) AS completedCount,
            AVG(TIMESTAMPDIFF(SECOND, d.picked_up_at, d.delivered_at)) / 60.0 AS avgMinutes
            FROM delivery d
            WHERE d.delivery_driver_id = :driverId
            AND d.status = 'DELIVERED'
            AND d.delivered_at IS NOT NULL
            AND d.picked_up_at IS NOT NULL
            """, nativeQuery = true)
    Object[] getDriverPerformanceStats(@Param("driverId") Integer driverId);

    @Query("""
    SELECT d FROM Delivery d
    WHERE d.isActive = true
    AND d.deliveryDriver.id = :driverId
    AND d.status = 'DELIVERED'
    AND d.deliveredAt >= :from
    AND d.deliveredAt < :to
""")
    List<Delivery> findDeliveriesByDriverAndDateRange(
            @Param("driverId") Integer driverId,
            @Param("from") Date from,
            @Param("to") Date to
    );
}
