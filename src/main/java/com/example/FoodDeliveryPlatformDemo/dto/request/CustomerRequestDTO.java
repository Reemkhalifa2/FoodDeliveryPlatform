package com.example.FoodDeliveryPlatformDemo.dto.request;

import com.example.FoodDeliveryPlatformDemo.entities.Customer;
import com.example.FoodDeliveryPlatformDemo.entities.CustomerAddress;
import com.example.FoodDeliveryPlatformDemo.utilities.HelperUtils;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@NoArgsConstructor
public class CustomerRequestDTO extends PersonDTO{
    @Nullable
    private CustomerAddressRequestDTO customerAddressRequestDTO;

    public static Customer toEntity(CustomerRequestDTO dto) {
        Customer customer = new Customer();

        customer.setFirstName(dto.getFirstName());
        customer.setLastName(dto.getLastName());
        customer.setEmail(dto.getEmail());
        customer.setPhone(dto.getPhone());
        customer.setPasswordHash(dto.getPassword());

        if (HelperUtils.isNull(customer.getCustomerAddresses())) {
            customer.setCustomerAddresses(new ArrayList<>());
        }

        if (HelperUtils.isNotNull(dto.getCustomerAddressRequestDTO()) ) {
            CustomerAddress customerAddress =
                    CustomerAddressRequestDTO.toEntity(dto.getCustomerAddressRequestDTO());

            customer.getCustomerAddresses().add(customerAddress);
        }

        return customer;
    }
}
