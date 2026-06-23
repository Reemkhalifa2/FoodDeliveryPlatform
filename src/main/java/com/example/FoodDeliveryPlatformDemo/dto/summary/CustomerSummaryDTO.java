package com.example.FoodDeliveryPlatformDemo.dto.summary;

import com.example.FoodDeliveryPlatformDemo.entities.Customer;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomerSummaryDTO {
    private String fullName;
    private Integer loyaltyPoints;

    public static CustomerSummaryDTO toSummary(Customer customer) {
        CustomerSummaryDTO dto = new CustomerSummaryDTO();
        dto.setFullName(customer.getFirstName()
                + " "
                + customer.getLastName()
        );
        dto.setLoyaltyPoints(customer.getLoyaltyPoints());
        return dto;
    }


}
