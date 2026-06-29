package com.example.FoodDeliveryPlatformDemo.controllers;

import com.example.FoodDeliveryPlatformDemo.dto.request.DriverPerformanceDTO;
import com.example.FoodDeliveryPlatformDemo.dto.request.NearbyDriverDTO;
import com.example.FoodDeliveryPlatformDemo.dto.response.DeliveryResponseDTO;
import com.example.FoodDeliveryPlatformDemo.enums.OrderStatus;
import com.example.FoodDeliveryPlatformDemo.services.DeliveryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("deliveries")
public class DeliveryController {

    public DeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    DeliveryService deliveryService;

    @PostMapping("/order/{orderId}/assign-manual/{driverId}")
    public ResponseEntity<DeliveryResponseDTO> assignDriverToOrder(@PathVariable Integer orderId , @PathVariable Integer driverId){
        DeliveryResponseDTO response = deliveryService.assignDriverToOrder(orderId, driverId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/order/{orderId}/assign-auto")
    public ResponseEntity<DeliveryResponseDTO> assignDriverToOrder(@PathVariable Integer orderId){
        DeliveryResponseDTO response = deliveryService.autoAssignDriver(orderId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeliveryResponseDTO> getById(@PathVariable Integer id){
        return ResponseEntity.ok(deliveryService.getById(id));
    }

    @PutMapping("/{id}/pickup")
    public ResponseEntity<DeliveryResponseDTO> markDeliveryPickedUp(@PathVariable Integer id){
        return ResponseEntity.ok(deliveryService.markDeliveryPickedUp(id));
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<DeliveryResponseDTO> markDeliveryDelivered(@PathVariable Integer id){
        return ResponseEntity.ok(deliveryService.markDeliveryDelivered(id));

    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<DeliveryResponseDTO>> getDeliveries(@PathVariable String status){
        return ResponseEntity.ok(deliveryService.getDeliveries(status));
    }

    @GetMapping("/nearby")
    public ResponseEntity<List<NearbyDriverDTO>> getNearbyDrivers(
            @RequestParam Double lat,
            @RequestParam Double lng,
            @RequestParam(defaultValue = "5.0") Double radiusKm
    ) {
        return ResponseEntity.ok(deliveryService.getNearbyDrivers(lat, lng, radiusKm));
    }

    @GetMapping("/{driverId}/performance")
    public ResponseEntity<DriverPerformanceDTO> getDriverPerformance(
            @PathVariable Integer driverId
    ) {
        return ResponseEntity.ok(deliveryService.getDriverPerformance(driverId));
    }


}
