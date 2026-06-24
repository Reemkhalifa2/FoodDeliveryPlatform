package com.example.FoodDeliveryPlatformDemo.dto.request;

import com.example.FoodDeliveryPlatformDemo.entities.Customer;
import com.example.FoodDeliveryPlatformDemo.entities.CustomerAddress;
import com.example.FoodDeliveryPlatformDemo.utilities.HelperUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@NoArgsConstructor
public class CustomerRequestDTO extends PersonDTO{
    CustomerAddressRequestDTO customerAddressRequestDTO;

    public static Customer toEntity(CustomerRequestDTO dto) {
        Customer customer = new Customer();
        customer.setFirstName(dto.getFirstName());
        customer.setLastName(dto.getLastName());
        customer.setEmail(dto.getEmail());
        customer.setPhone(dto.getPhone());
        customer.setPasswordHash(dto.getPassword());
        if (customer.getCustomerAddresses() == null) {
            customer.setCustomerAddresses(new ArrayList<>());
        }
        CustomerAddress address = CustomerAddressRequestDTO.toEntity(dto.getCustomerAddressRequestDTO());
        customer.getCustomerAddresses().add(address);
        return customer;
    }
}
