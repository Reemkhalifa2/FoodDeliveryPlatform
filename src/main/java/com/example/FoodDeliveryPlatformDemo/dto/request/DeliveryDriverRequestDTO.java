package com.example.FoodDeliveryPlatformDemo.dto.request;

import com.example.FoodDeliveryPlatformDemo.entities.Customer;
import com.example.FoodDeliveryPlatformDemo.entities.DeliveryDriver;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DeliveryDriverRequestDTO extends PersonDTO{
    @NotBlank
    private String vehicleType;
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]+$",
            message = "Vehicle plate must contain both letters and numbers"
    )
    private String vehiclePlate;

    public static DeliveryDriver toEntity(DeliveryDriverRequestDTO dto) {
        DeliveryDriver deliveryDriver = new DeliveryDriver();
        deliveryDriver.setFirstName(dto.getFirstName());
        deliveryDriver.setLastName(dto.getLastName());
        deliveryDriver.setEmail(dto.getEmail());
        deliveryDriver.setPhone(dto.getPhone());
        deliveryDriver.setPasswordHash(dto.getPassword());
        deliveryDriver.setVehiclePlate(dto.getVehiclePlate());
        deliveryDriver.setPasswordHash(dto.getVehicleType());
        return deliveryDriver;
    }



}
