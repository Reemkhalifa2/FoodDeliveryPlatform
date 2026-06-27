package com.example.FoodDeliveryPlatformDemo.controllers;

import com.example.FoodDeliveryPlatformDemo.dto.patch.CustomerPatchDTO;
import com.example.FoodDeliveryPlatformDemo.dto.request.CustomerAddressRequestDTO;
import com.example.FoodDeliveryPlatformDemo.dto.request.CustomerRequestDTO;
import com.example.FoodDeliveryPlatformDemo.dto.response.CustomerAddressResponseDTO;
import com.example.FoodDeliveryPlatformDemo.dto.response.CustomerResponseDTO;
import com.example.FoodDeliveryPlatformDemo.dto.response.OrderResponseDTO;
import com.example.FoodDeliveryPlatformDemo.entities.Customer;
import com.example.FoodDeliveryPlatformDemo.entities.Order;
import com.example.FoodDeliveryPlatformDemo.enums.OrderStatus;
import com.example.FoodDeliveryPlatformDemo.services.CustomerService;
import com.example.FoodDeliveryPlatformDemo.services.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("customer")
public class CustomerController {
    @Autowired
    public CustomerController(CustomerService customerService,
                              OrderService orderService) {
        this.customerService = customerService;
        this.orderService = orderService;
    }

    CustomerService customerService;
    OrderService orderService;
    @PatchMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> patchCustomer(@PathVariable Integer id , @RequestBody CustomerPatchDTO dto){
        return ResponseEntity.ok(customerService.patchCustomer(id,dto ));
    }
    @GetMapping("/{id}/orders/page")
    public ResponseEntity<Page<Order>> getOrderHistory(
            @PathVariable Integer id,
            @RequestParam OrderStatus status,
            @RequestParam String from,
            @RequestParam String to,
            @RequestParam Integer page,
            @RequestParam Integer size) {
        Page<Order> response = orderService.getOrders(id, status, from, to, page, size);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/search")
    public Page<Customer> search(
            @RequestParam String name,
            @RequestParam Integer page,
            @RequestParam Integer size) {

        return customerService.search(name, page, size);
    }

    @PostMapping
    public ResponseEntity<CustomerResponseDTO> createCustomer(@Valid @RequestBody CustomerRequestDTO customerRequestDTO) {
        CustomerResponseDTO response = customerService.createCustomer(customerRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponseDTO>> getAllCustomers() {
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    @GetMapping("{id}")
    public ResponseEntity<CustomerResponseDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(customerService.getById(id));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<CustomerResponseDTO> getByEmail(@PathVariable String email) {
        return ResponseEntity.ok(customerService.getByEmail(email));
    }

    @PutMapping("/{id}/deactivate")
    public ResponseEntity<String> deactivateCustomer(@PathVariable Integer id) {
        return ResponseEntity.ok(customerService.deactivateCustomer(id));
    }

    @PutMapping("/{id}/loyalty/add/{points}")
    public ResponseEntity<CustomerResponseDTO> addLoyaltyPoints(@PathVariable Integer id, @PathVariable Integer points) {
        return ResponseEntity.ok(customerService.updateLoyaltyPoints(id, points));
    }

    @PutMapping("/{id}/loyalty/deduct/{points}")
    public ResponseEntity<CustomerResponseDTO> applyLoyaltyPenalty(@PathVariable Integer id, @PathVariable Integer points) {
        return ResponseEntity.ok(customerService.applyLoyaltyPenalty(id, points));
    }

    @PostMapping("/{id}/addresses")
    public ResponseEntity<CustomerResponseDTO> addAddress(@PathVariable Integer id,@Valid @RequestBody CustomerAddressRequestDTO customerAddressRequestDTO) {
        CustomerResponseDTO response = customerService.addAddress(id, customerAddressRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}/addresses")
    public ResponseEntity<List<CustomerAddressResponseDTO>> getAllAddresses(@PathVariable Integer id) {
        return ResponseEntity.ok(customerService.getAllAddressesByCustomer(id));
    }

    @PutMapping("/addresses/{addressId}/default")
    public ResponseEntity<CustomerAddressResponseDTO> setDefault(@PathVariable Integer addressId) {
        return ResponseEntity.ok(customerService.setDefault(addressId));
    }

    //address for customer is not deleted
    @DeleteMapping("/addresses/{addressId}")
    public ResponseEntity<String> deleteAddress(@PathVariable Integer addressId) {
        return ResponseEntity.ok(customerService.deleteAddress(addressId));
    }

    @GetMapping("/{id}/orders")
    public ResponseEntity<List<OrderResponseDTO>> getAllOrders(@PathVariable Integer id) {
        return ResponseEntity.ok(customerService.getAllOrdersByCustomer(id));
    }

}
