package com.example.FoodDeliveryPlatformDemo.controllers;

import com.example.FoodDeliveryPlatformDemo.dto.response.CustomerAddressResponseDTO;
import com.example.FoodDeliveryPlatformDemo.dto.response.CustomerResponseDTO;
import com.example.FoodDeliveryPlatformDemo.dto.response.DeliveryDriverResponseDTO;
import com.example.FoodDeliveryPlatformDemo.services.CustomerService;
import com.example.FoodDeliveryPlatformDemo.services.DeliveryService;
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
                               OrderService orderService,
                               DeliveryService deliveryService) {
        this.restaurantService = restaurantService;
        this.customerService = customerService;
        this.orderService = orderService;
        this.deliveryService = deliveryService;
    }

    RestaurantService restaurantService;
    CustomerService customerService;
    OrderService orderService;
    DeliveryService deliveryService;

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

    @GetMapping("/customers/top-loyalty")
    public ResponseEntity<List<CustomerResponseDTO>> getTopLoyalCustomers(){
        return ResponseEntity.ok(customerService.getTopLoyalCustomers());
    }
    @GetMapping("/drivers/leaderboard")
    public ResponseEntity<List<DeliveryDriverResponseDTO>> getLeaderboard() {
        return ResponseEntity.ok(deliveryService.getLeaderboard());
    }

    @GetMapping("/platform/daily-summary")
    public ResponseEntity<String> totalLifetimeOrders(@RequestParam Date date){
        return ResponseEntity.ok(orderService.dailySummary(date));
    }







}
