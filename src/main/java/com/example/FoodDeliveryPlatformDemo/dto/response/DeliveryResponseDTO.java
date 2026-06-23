package com.example.FoodDeliveryPlatformDemo.dto.response;

import com.example.FoodDeliveryPlatformDemo.entities.Delivery;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class DeliveryResponseDTO {
    private String status;
    private Date pickedUpAt;
    private Date deliveredAt;

    public static DeliveryResponseDTO toResponse(Delivery delivery) {
        if (delivery == null) {
            return null;
        }

        DeliveryResponseDTO dto = new DeliveryResponseDTO();
        dto.setStatus(delivery.getStatus());
        dto.setPickedUpAt(delivery.getPickedUpAt());
        dto.setDeliveredAt(delivery.getDeliveredAt());
        return dto;
    }

    public static List<DeliveryResponseDTO> toResponse(List<Delivery> deliveries) {
        List<DeliveryResponseDTO> dtos = new ArrayList<>();

        if (deliveries != null) {
            for (Delivery delivery : deliveries) {
                dtos.add(toResponse(delivery));
            }
        }

        return dtos;
    }


}
