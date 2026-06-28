package com.example.FoodDeliveryPlatformDemo.controllers;

import com.example.FoodDeliveryPlatformDemo.dto.response.OrderHistoryResponseDTO;
import com.example.FoodDeliveryPlatformDemo.entities.OrderStatusHistory;
import com.example.FoodDeliveryPlatformDemo.entities.Order;
import com.example.FoodDeliveryPlatformDemo.enums.OrderStatus;
import com.example.FoodDeliveryPlatformDemo.dto.request.CorporateOrderRequestDTO;
import com.example.FoodDeliveryPlatformDemo.dto.request.OrderItemRequestDTO;
import com.example.FoodDeliveryPlatformDemo.dto.response.CorporateOrderResponseDTO;
import com.example.FoodDeliveryPlatformDemo.dto.response.OrderResponseDTO;
import com.example.FoodDeliveryPlatformDemo.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("orders")
public class OrderController {
    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    OrderService orderService;

    @GetMapping("/customer/{customerId}")
    public Page<Order> getCustomerOrders(
            @RequestParam(required = false) OrderStatus status,
            @RequestParam(required = false) String from,
            @RequestParam(required = false) String to,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return orderService.getOrders(status, from, to, page, size
        );
    }

    @PostMapping("/{id}/reorder")
    public ResponseEntity<OrderResponseDTO> reorder(@PathVariable Integer id) {
        OrderResponseDTO responseDTO = orderService.reorder(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }
    @GetMapping("/{id}/timeline")
    public ResponseEntity<List<OrderHistoryResponseDTO>> orderStatusHistory(@PathVariable Integer id){
            return ResponseEntity.ok(orderService.getTimeline(id));
    }
    @PostMapping("/customer/{customerId}/restaurant/{restaurantId}")
    public ResponseEntity<OrderResponseDTO> createOrder(
            @PathVariable Integer customerId,
            @PathVariable Integer restaurantId) {

        OrderResponseDTO response = orderService.createOrder(customerId, restaurantId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/{orderId}/items")
    public ResponseEntity<OrderResponseDTO> addItemToOrder(
            @PathVariable Integer orderId,
            @RequestBody List<OrderItemRequestDTO> orderItemRequestDTOS) {

        OrderResponseDTO response = orderService.addItemToOrder(orderId, orderItemRequestDTOS);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{id}/items/{itemId}")
    public ResponseEntity<OrderResponseDTO> removeMenuItemFromOrder(@PathVariable Integer id, @PathVariable Integer itemId){
        OrderResponseDTO response = orderService.removeMenuItemFromOrder(id, itemId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);

    }
    @PutMapping("/{id}/discount/{amount}")
    public ResponseEntity<OrderResponseDTO> applyDiscount(@PathVariable Integer id, @PathVariable Double amount){
        return ResponseEntity.ok(orderService.applyDiscount(id, amount));
    }

    @PutMapping("/{id}/confirm")
    public ResponseEntity<OrderResponseDTO> confirmOrder(@PathVariable Integer id) {
        return ResponseEntity.ok(orderService.confirmOrder(id));
    }
    @PutMapping("/{id}/status/{status}")
    public ResponseEntity<OrderResponseDTO> UpdateOrderStatus(@PathVariable Integer id , @PathVariable OrderStatus status){
        return ResponseEntity.ok(orderService.updateOrderStatus(id, status));
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<OrderResponseDTO> cancelOrder(@PathVariable Integer id){
        OrderResponseDTO response = orderService.cancelOrder(id);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);

    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> getById(@PathVariable Integer id){
        return ResponseEntity.ok(orderService.getById(id));
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<OrderResponseDTO>> findByRestaurantIdAndStatus(@PathVariable Integer restaurantId){
        return ResponseEntity.ok(orderService.findByRestaurantId(restaurantId));
    }

    @PostMapping("/corporate")
    public ResponseEntity<CorporateOrderResponseDTO> placeCorporateOrder(@RequestBody CorporateOrderRequestDTO corporateOrderRequestDTO){
        CorporateOrderResponseDTO responseDTO = orderService.placeCorporateOrder(corporateOrderRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }
















}
