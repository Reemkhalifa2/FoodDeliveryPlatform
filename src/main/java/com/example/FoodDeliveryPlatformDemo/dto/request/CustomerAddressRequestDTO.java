package com.example.FoodDeliveryPlatformDemo.dto.request;

import com.example.FoodDeliveryPlatformDemo.entities.Customer;
import com.example.FoodDeliveryPlatformDemo.entities.CustomerAddress;
import com.example.FoodDeliveryPlatformDemo.utilities.HelperUtils;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomerAddressRequestDTO {
    @NotBlank
    private String street;
    @NotBlank
    private String city;
    @NotBlank
    private String building;

    public static CustomerAddress toEntity(CustomerAddressRequestDTO dto) {
        CustomerAddress customerAddress = new CustomerAddress();
        customerAddress.setStreet(dto.getStreet());
        customerAddress.setCity(dto.getCity());
        customerAddress.setBuilding(dto.getBuilding());
        return customerAddress;
    }
}
