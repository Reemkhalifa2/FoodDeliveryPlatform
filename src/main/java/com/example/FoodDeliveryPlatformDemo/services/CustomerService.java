package com.example.FoodDeliveryPlatformDemo.services;

import com.example.FoodDeliveryPlatformDemo.dto.request.CustomerAddressRequestDTO;
import com.example.FoodDeliveryPlatformDemo.dto.request.CustomerRequestDTO;
import com.example.FoodDeliveryPlatformDemo.dto.response.CustomerResponseDTO;
import com.example.FoodDeliveryPlatformDemo.entities.Customer;
import com.example.FoodDeliveryPlatformDemo.exceptions.NullRequestBodyException;
import com.example.FoodDeliveryPlatformDemo.repositories.CustomerRepository;
import com.example.FoodDeliveryPlatformDemo.utilities.HelperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService{

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
    CustomerRepository customerRepository;

    public CustomerResponseDTO createCustomer(CustomerRequestDTO dto){
        if(HelperUtils.isNull(dto)){
            throw new NullRequestBodyException("Customer request cannot be null.");
        }
        Customer customer = CustomerRequestDTO.toEntity(dto);
        customerRepository.save(customer);
        return CustomerResponseDTO.toResponse(customer);
    }
    public CustomerResponseDTO createCustomer(CustomerRequestDTO dto, CustomerAddressRequestDTO initialAddress) {
        if(HelperUtils.isNull(dto)){
            throw new NullRequestBodyException("Customer request cannot be null.");
        }
        if(HelperUtils.isNull(initialAddress)){
            throw new NullRequestBodyException("Customer address request cannot be null.");
        }
        dto.setCustomerAddressRequestDTO(initialAddress);

        Customer customer = CustomerRequestDTO.toEntity(dto);
        customerRepository.save(customer);
        return CustomerResponseDTO.toResponse(customer);
    }





}
