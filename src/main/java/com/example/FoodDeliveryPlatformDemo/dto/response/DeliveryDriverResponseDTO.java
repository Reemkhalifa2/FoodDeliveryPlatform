package com.example.FoodDeliveryPlatformDemo.dto.response;

import com.example.FoodDeliveryPlatformDemo.entities.Customer;
import com.example.FoodDeliveryPlatformDemo.entities.DeliveryDriver;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class DeliveryDriverResponseDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String driverCode;
    private String vehicleType;
    private String vehiclePlate;

    public static DeliveryDriverResponseDTO toResponse(DeliveryDriver deliveryDriver) {
        DeliveryDriverResponseDTO dto = new DeliveryDriverResponseDTO();
        dto.setFirstName(deliveryDriver.getFirstName());
        dto.setLastName(deliveryDriver.getLastName());
        dto.setEmail(deliveryDriver.getEmail());
        dto.setPhone(deliveryDriver.getPhone());
        dto.setDriverCode(deliveryDriver.getDriverCode());
        dto.setVehiclePlate(deliveryDriver.getVehiclePlate());
        dto.setVehicleType(deliveryDriver.getVehicleType());
        dto.setCustomerAddressResponseDTOList(CustomerAddressResponseDTO.toResponse(deliveryDriver.getCustomerAddresses()));

        return dto;
    }

    public static List<DeliveryDriverResponseDTO> toResponse(List<DeliveryDriver> deliveryDrivers) {
        List<DeliveryDriverResponseDTO> dtos = new ArrayList<>();
        for (DeliveryDriver entity : deliveryDrivers) {
            dtos.add(toResponse(entity));
        }
        return dtos;
    }

}
