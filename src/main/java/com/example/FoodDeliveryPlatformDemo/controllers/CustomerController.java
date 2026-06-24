package com.example.FoodDeliveryPlatformDemo.controllers;

import com.example.FoodDeliveryPlatformDemo.dto.request.CustomerRequestDTO;
import com.example.FoodDeliveryPlatformDemo.dto.response.CustomerResponseDTO;
import com.example.FoodDeliveryPlatformDemo.services.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("customer")
public class CustomerController {
    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }
    CustomerService customerService;

    @PostMapping
    public ResponseEntity<CustomerResponseDTO> createCustomer(@Valid @RequestBody CustomerRequestDTO customerRequestDTO){
        return ResponseEntity.ok(customerService.createCustomer(customerRequestDTO));
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponseDTO>> getAllCustomers(){
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

}
