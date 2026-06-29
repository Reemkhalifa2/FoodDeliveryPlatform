package com.example.FoodDeliveryPlatformDemo.dto.request;

import com.example.FoodDeliveryPlatformDemo.entities.CorporateOrder;
import com.example.FoodDeliveryPlatformDemo.entities.OrderItem;
import com.example.FoodDeliveryPlatformDemo.enums.OrderStatus;
import com.example.FoodDeliveryPlatformDemo.utilities.HelperUtils;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CorporateOrderRequestDTO {

    @NotBlank(message = "Company name is required")
    private String companyName;

    @NotNull(message = "Restaurant is required")
    private Integer restaurantId;

    @NotEmpty(message = "Order must have at least one item")
    @Valid
    private List<OrderItemRequestDTO> items;

    public static CorporateOrder toEntity(CorporateOrderRequestDTO dto) {
        CorporateOrder corporateOrder = new CorporateOrder();
        corporateOrder.setCompanyName(dto.getCompanyName());
        corporateOrder.setCorporateCode(HelperUtils.generateId("Corporate", 4));

        if (HelperUtils.isNotNull(dto.getItems())) {
            List<OrderItem> orderItems = new ArrayList<>();
            for (OrderItemRequestDTO itemDto : dto.getItems()) {
                OrderItem item = OrderItemRequestDTO.toEntity(itemDto);
                orderItems.add(item);
            }
            corporateOrder.setItems(orderItems);
        }
        return corporateOrder;
    }
}