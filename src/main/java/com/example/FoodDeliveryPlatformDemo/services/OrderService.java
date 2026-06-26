package com.example.FoodDeliveryPlatformDemo.services;

import com.example.FoodDeliveryPlatformDemo.enums.OrderStatus;
import com.example.FoodDeliveryPlatformDemo.dto.request.CorporateOrderRequestDTO;
import com.example.FoodDeliveryPlatformDemo.dto.request.OrderItemRequestDTO;
import com.example.FoodDeliveryPlatformDemo.dto.response.CorporateOrderResponseDTO;
import com.example.FoodDeliveryPlatformDemo.dto.response.OrderResponseDTO;
import com.example.FoodDeliveryPlatformDemo.entities.*;
import com.example.FoodDeliveryPlatformDemo.exceptions.*;
import com.example.FoodDeliveryPlatformDemo.repositories.*;
import com.example.FoodDeliveryPlatformDemo.utilities.HelperUtils;
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

    public OrderResponseDTO createOrder(Integer customerId, Integer restaurantId) {
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
        order.setIsActive(true);
        order.setDeliveryFee(restaurant.getDeliveryFee());
        order.setStatus(OrderStatus.CREATED);
        order.setCreatedDate(new Date());
        orderRepository.save(order);
        return OrderResponseDTO.toResponse(order);
    }

    public OrderResponseDTO addItemToOrder(Integer orderId, List<OrderItemRequestDTO> items) {
        Order order = orderRepository.getById(orderId);
        if (HelperUtils.isNull(order)) {
            throw new OrderNotFoundException();
        }

        Double subTotal = order.getSubtotal() != null ? order.getSubtotal() : 0.0;

        if (HelperUtils.isNotNull(items)) {
            for (OrderItemRequestDTO dto : items) {
                MenuItem menuItem = menuItemRepository.getById(dto.getMenuItemId());
                if (HelperUtils.isNull(menuItem)) {
                    throw new MenuItemNotFoundException("Item Not Found");
                }

                // FIX 1: calculate item-level total first, then add to running subTotal
                double itemTotal = dto.getQuantity() * menuItem.getPrice();
                subTotal += itemTotal;

                OrderItem orderItem = OrderItemRequestDTO.toEntity(dto);
                orderItem.setUnitPrice(menuItem.getPrice());
                orderItem.setTotalPrice(itemTotal);   // FIX 1: was wrongly set to cumulative subTotal
                orderItem.setIsActive(true);
                orderItem.setCreatedDate(new Date());
                orderItemRepository.save(orderItem);
                order.getOrderItems().add(orderItem);
            }
        }

        order.setIsActive(true);
        order.setStatus(OrderStatus.CREATED);
        order.setUpdatedDate(new Date());
        order.setOrderCode(HelperUtils.generateId("Order-", 4));
        order.setSubtotal(subTotal);
        order.setTotalAmount(HelperUtils.calculateTotal(subTotal, order.getRestaurant().getDeliveryFee()));
        orderRepository.save(order);
        return OrderResponseDTO.toResponse(order);
    }

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
                MenuItem menuItem = menuItemRepository.getById(dto.getMenuItemId());
                if (HelperUtils.isNull(menuItem)) {
                    throw new MenuItemNotFoundException("Item Not Found");
                }

                // FIX 3: unitPrice and totalPrice were never set in this overload
                double itemTotal = dto.getQuantity() * menuItem.getPrice();
                subTotal += itemTotal;

                OrderItem orderItem = OrderItemRequestDTO.toEntity(dto);
                orderItem.setUnitPrice(menuItem.getPrice());   // FIX 3
                orderItem.setTotalPrice(itemTotal);            // FIX 3
                orderItem.setIsActive(true);
                orderItem.setCreatedDate(new Date());
                orderItemRepository.save(orderItem);
                order.getOrderItems().add(orderItem);
            }
        }

        order.setIsActive(true);
        order.setStatus(OrderStatus.CREATED);
        order.setCreatedDate(new Date());
        order.setOrderCode(HelperUtils.generateId("Order-", 4));
        order.setDeliveryFee(restaurant.getDeliveryFee());
        order.setSubtotal(subTotal);
        order.setTotalAmount(HelperUtils.calculateTotal(subTotal, restaurant.getDeliveryFee()));
        orderRepository.save(order);
        return OrderResponseDTO.toResponse(order);
    }

    public OrderResponseDTO createOrder(Integer customerId, Integer restaurantId,
                                        List<OrderItemRequestDTO> items, String notes) {
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
                MenuItem menuItem = menuItemRepository.getById(dto.getMenuItemId());
                if (HelperUtils.isNull(menuItem)) {
                    throw new MenuItemNotFoundException("Item Not Found");
                }

                double itemTotal = dto.getQuantity() * menuItem.getPrice();
                subTotal += itemTotal;

                OrderItem orderItem = OrderItemRequestDTO.toEntity(dto);
                orderItem.setUnitPrice(menuItem.getPrice());   // FIX 4
                orderItem.setTotalPrice(itemTotal);            // FIX 4
                orderItem.setIsActive(true);
                orderItem.setCreatedDate(new Date());
                orderItemRepository.save(orderItem);
                order.getOrderItems().add(orderItem);
            }
        }

        order.setIsActive(true);
        order.setStatus(OrderStatus.CREATED);
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

        OrderItem orderItem = new OrderItem();
        orderItem.setMenuItem(menuItem);
        orderItem.setQuantity(quantity);
        orderItem.setUnitPrice(menuItem.getPrice());
        orderItem.setTotalPrice(menuItem.getPrice() * quantity);
        orderItem.setIsActive(true);
        orderItem.setCreatedDate(new Date());
        orderItemRepository.save(orderItem);
        order.getOrderItems().add(orderItem);

        double itemTotal = menuItem.getPrice() * quantity;
        double newSubtotal = (order.getSubtotal() != null ? order.getSubtotal() : 0.0) + itemTotal;
        order.setSubtotal(newSubtotal);
        order.setTotalAmount(HelperUtils.calculateTotal(newSubtotal, order.getDeliveryFee()));  // FIX 6
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

        order.getOrderItems().remove(orderItem);

        double deductedAmount = HelperUtils.deductedAmount(orderItem.getUnitPrice(), orderItem.getQuantity());

        double newSubtotal = (order.getSubtotal() != null ? order.getSubtotal() : 0.0) - deductedAmount;
        order.setSubtotal(newSubtotal);
        order.setTotalAmount(HelperUtils.calculateTotal(newSubtotal, order.getDeliveryFee()));  // FIX 7

        order.setUpdatedDate(new Date());

        orderItem.setIsActive(false);
        orderItem.setUpdatedDate(new Date());
        orderItemRepository.save(orderItem);
        orderRepository.save(order);

        return OrderResponseDTO.toResponse(order);
    }

    public OrderResponseDTO applyDiscount(Integer orderId, double discountAmount) {
        Order order = orderRepository.getById(orderId);
        if (HelperUtils.isNull(order)) {
            throw new OrderNotFoundException();
        }
        if(discountAmount>order.getTotalAmount()){
            order.setTotalAmount(0.0);
        }
        Double totalAmount = HelperUtils.calculateTotal(order.getTotalAmount(), order.getDeliveryFee(), discountAmount);
        order.setTotalAmount(totalAmount);
        order.setUpdatedDate(new Date());
        orderRepository.save(order);
        return OrderResponseDTO.toResponse(order);
    }


    public OrderResponseDTO updateOrderStatus(Integer orderId, OrderStatus newStatus) {
        Order order = orderRepository.getById(orderId);
        if (HelperUtils.isNull(order)) {
            throw new OrderNotFoundException();
        }
        order.setStatus(newStatus);
        order.setUpdatedDate(new Date());
        orderRepository.save(order);
        return OrderResponseDTO.toResponse(order);
    }

    public OrderResponseDTO cancelOrder(Integer orderId) {
        Order order = orderRepository.getById(orderId);
        if (HelperUtils.isNull(order)) {
            throw new OrderNotFoundException();
        }
        if (!order.getStatus().equals(OrderStatus.PENDING)) {
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

        Double subTotal = 0.0;
        for (OrderItem item : order.getOrderItems()) {
            subTotal += item.getUnitPrice() * item.getQuantity();
        }

        order.setSubtotal(subTotal);

        Double deliveryFee = (order.getRestaurant() != null && order.getRestaurant().getDeliveryFee() != null)
                ? order.getRestaurant().getDeliveryFee()
                : 0.0;

        order.setTotalAmount(subTotal + deliveryFee);
        order.setUpdatedDate(new Date());
        orderRepository.save(order);
        return OrderResponseDTO.toResponse(order);
    }

    public CorporateOrderResponseDTO placeCorporateOrder(CorporateOrderRequestDTO dto) {
        CorporateOrder corporateOrder = CorporateOrderRequestDTO.toEntity(dto);
        if(HelperUtils.isNull(corporateOrder)){
            throw new OrderNotFoundException();
        }
        List<OrderItem> orderItems = corporateOrder.getItems();
        orderItemRepository.saveAll(orderItems);
        corporateOrder.setIsActive(true);
        corporateOrder.setCreatedDate(new Date());
        corporateOrderRepository.save(corporateOrder);
        return CorporateOrderResponseDTO.toResponse(corporateOrder);
    }

    public OrderResponseDTO confirmOrder(Integer orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException());

        if (order.getStatus() != OrderStatus.CREATED) {
            throw new InvalidOrderStateException("Only CREATED orders can be confirmed");
        }

        order.setStatus(OrderStatus.CONFIRMED);
        order.setUpdatedDate(new Date());
        order = orderRepository.save(order);
        return OrderResponseDTO.toResponse(order);
    }
    public OrderResponseDTO getById(Integer id){
        Order order = orderRepository.getById(id);
        if(HelperUtils.isNull(order)){
            throw new OrderNotFoundException();
        }
        return OrderResponseDTO.toResponse(order);
    }

    public List<OrderResponseDTO> findByRestaurantIdAndStatus(Integer id){
        List<Order> orders = orderRepository.findByRestaurantIdAndStatus(id);
        return OrderResponseDTO.toResponse(orders);
    }



}