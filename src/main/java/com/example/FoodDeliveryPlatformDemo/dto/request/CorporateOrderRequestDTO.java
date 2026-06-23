package com.example.FoodDeliveryPlatformDemo.dto.request;

import com.example.FoodDeliveryPlatformDemo.entities.CorporateOrder;
import com.example.FoodDeliveryPlatformDemo.entities.OrderItem;
import com.example.FoodDeliveryPlatformDemo.utilities.HelperUtils;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CorporateOrderRequestDTO {
    @NotBlank
    private String companyName;
    private List<OrderItemRequestDTO> items;

    public static CorporateOrder toEntity(CorporateOrderRequestDTO dto) {
        CorporateOrder corporateOrder = new CorporateOrder();
        corporateOrder.setCompanyName(dto.getCompanyName());
        List<OrderItem> items = new ArrayList<>();
        if (HelperUtils.isNotNull(dto.getItems()) ) {
            for (OrderItemRequestDTO itemDto : dto.getItems()) {
                items.add(OrderItemRequestDTO.toEntity(itemDto));
            }
        }

        corporateOrder.setItems(items);
        return corporateOrder;

    }

}
