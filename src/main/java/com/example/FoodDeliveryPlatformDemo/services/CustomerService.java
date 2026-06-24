package com.example.FoodDeliveryPlatformDemo.services;

import com.example.FoodDeliveryPlatformDemo.dto.request.CustomerAddressRequestDTO;
import com.example.FoodDeliveryPlatformDemo.dto.request.CustomerRequestDTO;
import com.example.FoodDeliveryPlatformDemo.dto.response.CustomerResponseDTO;
import com.example.FoodDeliveryPlatformDemo.entities.Customer;
import com.example.FoodDeliveryPlatformDemo.entities.CustomerAddress;
import com.example.FoodDeliveryPlatformDemo.exceptions.CustomerNotFoundException;
import com.example.FoodDeliveryPlatformDemo.exceptions.InvalidLoyaltyPointsException;
import com.example.FoodDeliveryPlatformDemo.exceptions.NullRequestBodyException;
import com.example.FoodDeliveryPlatformDemo.repositories.CustomerAddressRepository;
import com.example.FoodDeliveryPlatformDemo.repositories.CustomerRepository;
import com.example.FoodDeliveryPlatformDemo.utilities.HelperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService{

    @Autowired
    public CustomerService(CustomerRepository customerRepository, CustomerAddressRepository customerAddressRepository) {
        this.customerRepository = customerRepository;
        this.customerAddressRepository = customerAddressRepository;
    }
    CustomerRepository customerRepository;
    CustomerAddressRepository customerAddressRepository;

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
        CustomerAddress address = CustomerAddressRequestDTO.toEntity(initialAddress);

        customerRepository.save(customer);
        customerAddressRepository.save(address);
        return CustomerResponseDTO.toResponse(customer);
    }

    public CustomerResponseDTO addAddress(Integer customerId, CustomerAddressRequestDTO address){
        if(HelperUtils.isNull(address)){
            throw new NullRequestBodyException("address request body is null");
        }
        Customer customer = customerRepository.findByID(customerId);
        if(HelperUtils.isNull(customer)){
            throw new CustomerNotFoundException(customerId);
        }
        CustomerAddress customerAddress = CustomerAddressRequestDTO.toEntity(address);
        customer.getCustomerAddresses().add(customerAddress);
        customerRepository.save(customer);
        customerAddressRepository.save(customerAddress);

        return CustomerResponseDTO.toResponse(customer);
    }



    public CustomerResponseDTO updateLoyaltyPoints(Integer customerId, int points){
        Customer customer = customerRepository.findByID(customerId);
        if(HelperUtils.isNull(customer)){
            throw new CustomerNotFoundException(customerId);
        }
        if (points < 0) {
            throw new InvalidLoyaltyPointsException("Points must be positive");
        }
        if (points < customer.getLoyaltyPoints()) {
            throw new InvalidLoyaltyPointsException("Enter new value");
        }
        customer.setLoyaltyPoints(points);
        customerRepository.save(customer);
        return CustomerResponseDTO.toResponse(customer);
    }











}
