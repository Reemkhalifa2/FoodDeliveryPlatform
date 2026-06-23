package com.example.FoodDeliveryPlatformDemo.dto.summary;

import com.example.FoodDeliveryPlatformDemo.dto.response.RestaurantResponseDTO;
import com.example.FoodDeliveryPlatformDemo.entities.Customer;
import com.example.FoodDeliveryPlatformDemo.entities.CustomerAddress;
import com.example.FoodDeliveryPlatformDemo.entities.Restaurant;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

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

    public static List<CustomerAddressSummaryDTO> toSummary(List<CustomerAddress> addresses) {
        List<CustomerAddressSummaryDTO> dtos = new ArrayList<>();

        for (CustomerAddress entity : addresses) {
            dtos.add(toSummary(entity));
        }

        return dtos;
    }
}
