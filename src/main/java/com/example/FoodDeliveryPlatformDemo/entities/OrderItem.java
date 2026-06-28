package com.example.FoodDeliveryPlatformDemo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem extends BaseEntity{
    private Integer quantity;
    private Double unitPrice;
    private Double totalPrice;
    private String specialInstructions ;

    @ManyToOne
    private Order order;
    @ManyToOne CorporateOrder corporateOrder;
    @ManyToOne
    private MenuItem menuItem;

}
