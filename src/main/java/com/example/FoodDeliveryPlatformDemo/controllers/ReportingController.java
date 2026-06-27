package com.example.FoodDeliveryPlatformDemo.controllers;

import com.example.FoodDeliveryPlatformDemo.dto.response.CustomerAddressResponseDTO;
import com.example.FoodDeliveryPlatformDemo.dto.response.CustomerResponseDTO;
import com.example.FoodDeliveryPlatformDemo.services.CustomerService;
import com.example.FoodDeliveryPlatformDemo.services.OrderService;
import com.example.FoodDeliveryPlatformDemo.services.RestaurantService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("reports")
public class ReportingController {

    @Autowired
    public ReportingController(RestaurantService restaurantService,
                               CustomerService customerService,
                               OrderService orderService) {
        this.restaurantService = restaurantService;
        this.customerService = customerService;
        this.orderService = orderService;
    }

    RestaurantService restaurantService;
    CustomerService customerService;
    OrderService orderService;

    @GetMapping("/revenue/restaurant/{restaurantId}")
    public ResponseEntity<String> getRestaurantRevenue(
            @PathVariable Integer restaurantId,
            @RequestParam Date date) {

        return ResponseEntity.ok(
                restaurantService.getRestaurantRevenue(restaurantId, date)
        );
    }
    @GetMapping("/orders/count/restaurant/{restaurantId}")
    public ResponseEntity<Integer> totalLifetimeOrders(@PathVariable Integer restaurantId){
        return ResponseEntity.ok(restaurantService.totalLifetimeOrders(restaurantId));
    }

    @GetMapping("/drivers/leaderboard")
    public ResponseEntity<List<CustomerResponseDTO>> totalLifetimeOrders(){
        return ResponseEntity.ok(customerService.getTopLoyalCustomers());
    }

    @GetMapping("/platform/daily-summary")
    public ResponseEntity<String> totalLifetimeOrders(@RequestParam Date date){
        return ResponseEntity.ok(orderService.dailySummary(date));
    }







}
