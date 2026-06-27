package com.example.FoodDeliveryPlatformDemo.entities;

import com.example.FoodDeliveryPlatformDemo.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatusHistory extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private Date changedAt;
}
