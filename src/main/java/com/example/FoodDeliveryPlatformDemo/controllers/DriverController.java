package com.example.FoodDeliveryPlatformDemo.controllers;

import com.example.FoodDeliveryPlatformDemo.dto.request.DeliveryDriverRequestDTO;
import com.example.FoodDeliveryPlatformDemo.dto.response.DeliveryDriverResponseDTO;
import com.example.FoodDeliveryPlatformDemo.dto.response.DeliveryResponseDTO;
import com.example.FoodDeliveryPlatformDemo.entities.Delivery;
import com.example.FoodDeliveryPlatformDemo.services.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/drivers")
public class DriverController {

    @Autowired
    public DriverController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }
    DeliveryService deliveryService;


    @PostMapping
    public ResponseEntity<DeliveryDriverResponseDTO> createDriver(@RequestBody DeliveryDriverRequestDTO dto){
        DeliveryDriverResponseDTO response = deliveryService.createDriver(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<DeliveryDriverResponseDTO>> getAll(){
        return ResponseEntity.ok(deliveryService.getAll());
    }

    @GetMapping("/online")
    public ResponseEntity<List<DeliveryDriverResponseDTO>> getByIsOnline(){
        return ResponseEntity.ok(deliveryService.getByIsOnline());
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<DeliveryDriverResponseDTO> toggleOnlineStatus(@PathVariable Integer id , @RequestParam Boolean status){
        return ResponseEntity.ok(deliveryService.toggleDriverOnlineStatus(id ,status));
    }

    @PutMapping("/{id}/location")
    public ResponseEntity<DeliveryDriverResponseDTO> updateDriverLocation(@PathVariable Integer id , @RequestParam Double lat , @RequestParam Double lng){
        return ResponseEntity.ok(deliveryService.updateDriverLocation(id ,lat, lng));

    }

    @GetMapping("/{id}/deliveries")
    public ResponseEntity<List<DeliveryResponseDTO>> driverDeliveryHistory(@PathVariable Integer id){
        return ResponseEntity.ok(deliveryService.driverDeliveryHistory(id));
    }

    @GetMapping("/{id}/deliveries/active")
    public ResponseEntity<List<DeliveryResponseDTO>> getActiveDelivery(@PathVariable Integer id){
        return ResponseEntity.ok(deliveryService.getActiveDelivery(id));
    }


}
