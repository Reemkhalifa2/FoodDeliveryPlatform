package com.example.FoodDeliveryPlatformDemo.dto.summary;

import com.example.FoodDeliveryPlatformDemo.entities.Customer;
import com.example.FoodDeliveryPlatformDemo.entities.CustomerAddress;
import lombok.Data;

@Data
public class CustomerAddressSummaryDTO {

    private String address;

    public static CustomerAddressSummaryDTO toSummary(CustomerAddress address) {
        CustomerAddressSummaryDTO dto = new CustomerAddressSummaryDTO();
        dto.setAddress(address.getCity()
                + " , "
                + address.getStreet()
                + " , "
                + address.getBuilding()
        );
        return dto;
    }
}
