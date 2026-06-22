package com.example.FoodDeliveryPlatformDemo.dto.response;

import com.example.FoodDeliveryPlatformDemo.dto.request.CustomerRequest;
import com.example.FoodDeliveryPlatformDemo.entities.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class CustomerResponse {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private Integer loyaltyPoints;
    private String customerCode;
    private Date createdDate;

    public CustomerResponse fromEntity(Customer entity){
        CustomerResponse dto = new CustomerResponse();


    }

}