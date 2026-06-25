package com.example.FoodDeliveryPlatformDemo.services;

import com.example.FoodDeliveryPlatformDemo.OrderStatus;
import com.example.FoodDeliveryPlatformDemo.dto.request.CorporateOrderRequestDTO;
import com.example.FoodDeliveryPlatformDemo.dto.request.OrderItemRequestDTO;
import com.example.FoodDeliveryPlatformDemo.dto.response.CorporateOrderResponseDTO;
import com.example.FoodDeliveryPlatformDemo.dto.response.OrderResponseDTO;
import com.example.FoodDeliveryPlatformDemo.entities.*;
import com.example.FoodDeliveryPlatformDemo.exceptions.*;
import com.example.FoodDeliveryPlatformDemo.repositories.*;
import com.example.FoodDeliveryPlatformDemo.utilities.HelperUtils;
import org.aspectj.weaver.ast.Or;
import org.hibernate.sql.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    public OrderService(OrderRepository orderRepository,
                        CustomerRepository customerRepository,
                        RestaurantRepository restaurantRepository,
                        MenuItemRepository menuItemRepository,
                        OrderItemRepository orderItemRepository,
                        CorporateOrderRepository corporateOrderRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.restaurantRepository = restaurantRepository;
        this.menuItemRepository = menuItemRepository;
        this.orderItemRepository = orderItemRepository;
        this.corporateOrderRepository = corporateOrderRepository;
    }

    OrderRepository orderRepository;
    CustomerRepository customerRepository;
    RestaurantRepository restaurantRepository;
    MenuItemRepository menuItemRepository;
    OrderItemRepository orderItemRepository;
    CorporateOrderRepository corporateOrderRepository;
    public OrderResponseDTO createOrder(Integer customerId, Integer restaurantId, List<OrderItemRequestDTO> items) {
        Customer customer = customerRepository.findByID(customerId);
        if (HelperUtils.isNull(customer)) {
            throw new CustomerNotFoundException();
        }
        Restaurant restaurant = restaurantRepository.getById(restaurantId);
        if (HelperUtils.isNull(restaurant)) {
            throw new RestaurantNotFoundException();
        }
        Order order = new Order();
        order.setCustomer(customer);
        order.setRestaurant(restaurant);
        Double subTotal = 0.0;
        if (HelperUtils.isNotNull(items)) {
            for (OrderItemRequestDTO dto : items) {
                subTotal += dto.getQuantity() * dto.getMenuItemRequestDTO().getPrice();
                OrderItem orderItem = OrderItemRequestDTO.toEntity(dto);
                orderItem.setIsActive(true);
                orderItem.setCreatedDate(new Date());
                orderItemRepository.save(orderItem);
                order.getOrderItems().add(orderItem);
            }
        }
        order.setIsActive(true);
        order.setStatus(OrderStatus.PENDING);
        order.setCreatedDate(new Date());
        order.setOrderCode(HelperUtils.generateId("Order-", 4));
        order.setDeliveryFee(restaurant.getDeliveryFee());
        order.setSubtotal(subTotal);
        order.setTotalAmount(HelperUtils.calculateTotal(subTotal, restaurant.getDeliveryFee()));
        orderRepository.save(order);
        return OrderResponseDTO.toResponse(order);
    }

    public OrderResponseDTO createOrder(Integer customerId, Integer restaurantId, List<OrderItemRequestDTO> items,
                                        String notes) {
        Customer customer = customerRepository.findByID(customerId);
        if (HelperUtils.isNull(customer)) {
            throw new CustomerNotFoundException();
        }
        Restaurant restaurant = restaurantRepository.getById(restaurantId);
        if (HelperUtils.isNull(restaurant)) {
            throw new RestaurantNotFoundException();
        }
        Order order = new Order();
        order.setCustomer(customer);
        order.setRestaurant(restaurant);
        Double subTotal = 0.0;
        if (HelperUtils.isNotNull(items)) {
            for (OrderItemRequestDTO dto : items) {
                subTotal += dto.getQuantity() * dto.getMenuItemRequestDTO().getPrice();
                OrderItem orderItem = OrderItemRequestDTO.toEntity(dto);
                orderItem.setIsActive(true);
                orderItem.setCreatedDate(new Date());
                orderItemRepository.save(orderItem);
                order.getOrderItems().add(orderItem);
            }
        }
        order.setIsActive(true);
        order.setStatus(OrderStatus.PENDING);
        order.setCreatedDate(new Date());
        order.setOrderCode(HelperUtils.generateId("Order-", 4));
        order.setDeliveryFee(restaurant.getDeliveryFee());
        order.setSubtotal(subTotal);
        order.setDeliveryNotes(notes);
        order.setTotalAmount(HelperUtils.calculateTotal(subTotal, restaurant.getDeliveryFee()));
        orderRepository.save(order);
        return OrderResponseDTO.toResponse(order);
    }

    public OrderResponseDTO addMenuItemToOrder(Integer orderId, Integer menuItemId, int quantity) {
        Order order = orderRepository.getById(orderId);
        if (HelperUtils.isNull(order)) {
            throw new OrderNotFoundException();
        }
        MenuItem menuItem = menuItemRepository.getById(menuItemId);
        if (HelperUtils.isNull(menuItem)) {
            throw new MenuItemNotFoundException("Item Not Found");
        }
        Double subTotal = HelperUtils.calculateTotal(menuItem.getPrice(), quantity);
        order.setSubtotal(subTotal);
        order.setUpdatedDate(new Date());
        orderRepository.save(order);
        return OrderResponseDTO.toResponse(order);

    }

    public OrderResponseDTO removeMenuItemFromOrder(Integer orderId, Integer orderItemId) {
        Order order = orderRepository.getById(orderId);
        if (HelperUtils.isNull(order)) {
            throw new OrderNotFoundException();
        }
        OrderItem orderItem = orderItemRepository.getById(orderItemId);

        if (HelperUtils.isNull(orderItem)) {
            throw new ObjectNotFoundException("Order Item Not Found.");
        }

        if (!orderItem.getOrder().getId().equals(orderId)) {
            throw new IllegalArgumentException(
                    "Order item does not belong to this order");
        }

        order.getOrderItems().remove(orderItem);
        Double deductedAmount = HelperUtils.deductedAmount(orderItem.getUnitPrice() , orderItem.getQuantity());

        order.setTotalAmount(order.getTotalAmount() - deductedAmount);

        order.setUpdatedDate(new Date());

        orderItem.setIsActive(false);
        orderItem.setUpdatedDate(new Date());
        orderItemRepository.save(orderItem);
        orderRepository.save(order);

        return OrderResponseDTO.toResponse(order);
    }

    public OrderResponseDTO applyDiscount(Integer orderId, double discountAmount){
        Order order = orderRepository.getById(orderId);
        if(HelperUtils.isNull(order)){
            throw new OrderNotFoundException();
        }
        Double totalAmount = HelperUtils.calculateTotal(order.getSubtotal() , order.getDeliveryFee() , discountAmount);
        order.setTotalAmount(totalAmount);
        orderRepository.save(order);
        return OrderResponseDTO.toResponse(order);
    }
    public OrderResponseDTO updateOrderStatus(Integer orderId, OrderStatus newStatus){
        Order order = orderRepository.getById(orderId);
        if(HelperUtils.isNull(order)){
            throw new OrderNotFoundException();
        }
        order.setStatus(newStatus);
        orderRepository.save(order);
        return OrderResponseDTO.toResponse(order);
    }
    public OrderResponseDTO cancelOrder(Integer orderId){
        Order order = orderRepository.getById(orderId);
        if(HelperUtils.isNull(order)){
            throw new OrderNotFoundException();
        }
        if(!order.getStatus().equals(OrderStatus.PENDING)){
            throw new InvalidOrderStateException();
        }
        order.setStatus(OrderStatus.CANCELLED);
        order.setUpdatedDate(new Date());
        orderRepository.save(order);
        return OrderResponseDTO.toResponse(order);
    }

    public OrderResponseDTO calculateOrderTotals(Integer orderId) {
        Order order = orderRepository.getById(orderId);
        if (HelperUtils.isNull(order)) {
            throw new OrderNotFoundException();
        }
        Double total = 0.0;

        for (OrderItem item : order.getOrderItems()) {
            total += item.getUnitPrice() * item.getQuantity();
        }

        if (order.getRestaurant() != null && order.getRestaurant().getDeliveryFee() != null) {
            total += order.getRestaurant().getDeliveryFee();
        }
        order.setTotalAmount(total);
        order.setUpdatedDate(new Date());
        orderRepository.save(order);
        return OrderResponseDTO.toResponse(order);
    }

    public CorporateOrderResponseDTO placeCorporateOrder(CorporateOrderRequestDTO dto){
        CorporateOrder corporateOrder = CorporateOrderRequestDTO.toEntity(dto);
        corporateOrder.setIsActive(true);
        corporateOrder.setCreatedDate(new Date());
        corporateOrderRepository.save(corporateOrder);
        return CorporateOrderResponseDTO.toResponse(corporateOrder);

    }








}
