package com.example.FoodDeliveryPlatformDemo.services;

import com.example.FoodDeliveryPlatformDemo.dto.request.CustomerAddressRequestDTO;
import com.example.FoodDeliveryPlatformDemo.dto.request.CustomerRequestDTO;
import com.example.FoodDeliveryPlatformDemo.dto.response.CustomerResponseDTO;
import com.example.FoodDeliveryPlatformDemo.entities.Customer;
import com.example.FoodDeliveryPlatformDemo.entities.CustomerAddress;
import com.example.FoodDeliveryPlatformDemo.exceptions.*;
import com.example.FoodDeliveryPlatformDemo.repositories.CustomerAddressRepository;
import com.example.FoodDeliveryPlatformDemo.repositories.CustomerRepository;
import com.example.FoodDeliveryPlatformDemo.utilities.HelperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;
import java.util.List;

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
        customer.setIsActive(true);
        customer.setCustomerCode(HelperUtils.generateId("CUST-",4));
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

        Customer customer = CustomerRequestDTO.toEntity(dto);
        CustomerAddress address = CustomerAddressRequestDTO.toEntity(initialAddress);
        customer.getCustomerAddresses().add(address);
        customerAddressRepository.save(address);

        customerRepository.save(customer);
        return CustomerResponseDTO.toResponse(customer);
    }

    public CustomerResponseDTO addAddress(Integer customerId, CustomerAddressRequestDTO address){

        if(HelperUtils.isNull(address)){
            throw new NullRequestBodyException("address request body is null");
        }

        Customer customer = customerRepository.findByID(customerId);
        if(HelperUtils.isNull(customer)){
            throw new CustomerNotFoundException();
        }
        CustomerAddress customerAddress = CustomerAddressRequestDTO.toEntity(address);
        customer.getCustomerAddresses().add(customerAddress);
        customerAddressRepository.save(customerAddress);
        customerRepository.save(customer);

        return CustomerResponseDTO.toResponse(customer);
    }



    public CustomerResponseDTO updateLoyaltyPoints(Integer customerId, int points){
        Customer customer = customerRepository.findByID(customerId);
        if(HelperUtils.isNull(customer)){
            throw new CustomerNotFoundException();
        }
        if (points < 0) {
            throw new InvalidLoyaltyPointsException("Points must be positive");
        }

        customer.setLoyaltyPoints(points);
        customerRepository.save(customer);
        return CustomerResponseDTO.toResponse(customer);
    }

    public CustomerResponseDTO applyLoyaltyPenalty(Integer customerId, int pointsDeducted) {
        Customer customer = customerRepository.findByID(customerId);
        if(HelperUtils.isNull(customer)){
            throw new CustomerNotFoundException();
        }
        if (pointsDeducted < 0) {
            throw new InvalidLoyaltyPointsException("Points must be positive");
        }
        if (pointsDeducted > customer.getLoyaltyPoints()) {
            throw new InvalidLoyaltyPointsException("Not enough points");
        }
        customer.setLoyaltyPoints(HelperUtils.subtract(customer.getLoyaltyPoints(), pointsDeducted));
        customerRepository.save(customer);
        return CustomerResponseDTO.toResponse(customer);
    }

    public String deactivateCustomer(Integer customerId){
        Customer customer = customerRepository.findByID(customerId);
        if(HelperUtils.isNull(customer)){
            throw new CustomerNotFoundException();
        }
        customer.setIsActive(false);
        customerRepository.save(customer);
        return "Customer deactivated successfully";
    }

    public List<CustomerResponseDTO> getAllCustomers(){
        return CustomerResponseDTO.toResponse(customerRepository.findAll());
    }
    public CustomerResponseDTO getById(Integer id){
        if(HelperUtils.isNull(id)){
            throw new InvalidRequestException("Id is null");
        }
        Customer customer = customerRepository.findByID(id);
        if(HelperUtils.isNull(customer)){
            throw new CustomerNotFoundException();
        }
        return CustomerResponseDTO.toResponse(customer);
    }

    public CustomerResponseDTO getByEmail(String email){
        if(HelperUtils.isNull(email)){
            throw new InvalidRequestException("email is null");
        }
        Customer customer = customerRepository.findByEmail(email);
        if(HelperUtils.isNull(customer)){
            throw new CustomerNotFoundException();
        }
        return CustomerResponseDTO.toResponse(customer);
    }

















}
