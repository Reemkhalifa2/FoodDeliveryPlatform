package com.example.FoodDeliveryPlatformDemo.dto.summary;

import com.example.FoodDeliveryPlatformDemo.entities.Customer;
import com.example.FoodDeliveryPlatformDemo.entities.DeliveryDriver;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DeliveryDriverSummaryDTO {
    private String fullName;
    private String Vehicle;

    public static DeliveryDriverSummaryDTO toSummary(DeliveryDriver deliveryDriver) {
        DeliveryDriverSummaryDTO dto = new DeliveryDriverSummaryDTO();
        dto.setFullName(deliveryDriver.getFirstName()
                + " "
                + deliveryDriver.getLastName()
        );
        dto.setVehicle("Vehicle type : " +deliveryDriver.getVehicleType()
                + " Plate Number"
                + deliveryDriver.getVehiclePlate()
        );
        return dto;
    }
}
