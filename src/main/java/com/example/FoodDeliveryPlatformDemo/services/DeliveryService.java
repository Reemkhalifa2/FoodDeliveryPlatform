package com.example.FoodDeliveryPlatformDemo.services;

import com.example.FoodDeliveryPlatformDemo.dto.request.DeliveryDriverRequestDTO;
import com.example.FoodDeliveryPlatformDemo.dto.request.DriverPerformanceDTO;
import com.example.FoodDeliveryPlatformDemo.dto.request.NearbyDriverDTO;
import com.example.FoodDeliveryPlatformDemo.dto.response.DeliveryDriverResponseDTO;
import com.example.FoodDeliveryPlatformDemo.dto.response.DeliveryResponseDTO;
import com.example.FoodDeliveryPlatformDemo.entities.Delivery;
import com.example.FoodDeliveryPlatformDemo.entities.DeliveryDriver;
import com.example.FoodDeliveryPlatformDemo.entities.Order;
import com.example.FoodDeliveryPlatformDemo.entities.OrderStatusHistory;
import com.example.FoodDeliveryPlatformDemo.enums.OrderStatus;
import com.example.FoodDeliveryPlatformDemo.exceptions.InvalidRequestException;
import com.example.FoodDeliveryPlatformDemo.exceptions.ObjectNotFoundException;
import com.example.FoodDeliveryPlatformDemo.exceptions.OrderNotFoundException;
import com.example.FoodDeliveryPlatformDemo.repositories.*;
import com.example.FoodDeliveryPlatformDemo.utilities.HelperUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class DeliveryService {

    DriverRepository driverRepository;

    public DeliveryService(StatusHistoryRepository statusHistoryRepository,
                           DeliveryRepository deliveryRepository,
                           DriverRepository driverRepository,OrderRepository orderRepository,
                           ReviewRepository reviewRepository) {
        this.deliveryRepository = deliveryRepository;
        this.driverRepository = driverRepository;
        this.orderRepository = orderRepository;
        this.statusHistoryRepository = statusHistoryRepository;
        this.reviewRepository = reviewRepository;
    }

    DeliveryRepository deliveryRepository;
    OrderRepository orderRepository;
    StatusHistoryRepository statusHistoryRepository;
    ReviewRepository reviewRepository;

    public List<DeliveryDriverResponseDTO> getLeaderboard() {
        List<DeliveryDriver> drivers = driverRepository.findTopDriversByCompletedDeliveries();
        return DeliveryDriverResponseDTO.toResponse(drivers);
    }
    public DeliveryDriverResponseDTO createDriver(DeliveryDriverRequestDTO dto){
        DeliveryDriver deliveryDriver = DeliveryDriverRequestDTO.toEntity(dto);
        if (driverRepository.existsByEmail(deliveryDriver.getEmail())) {
            throw new InvalidRequestException("Email already exists");
        }
        deliveryDriver.setCreatedDate(new Date());
        deliveryDriver.setIsActive(true);

        driverRepository.save(deliveryDriver);
        return DeliveryDriverResponseDTO.toResponse(deliveryDriver);
    }

    public List<DeliveryDriverResponseDTO> getAll(){
        return DeliveryDriverResponseDTO.toResponse(driverRepository.getAll());
    }

    public List<DeliveryDriverResponseDTO> getByIsOnline(){
        return DeliveryDriverResponseDTO.toResponse(driverRepository.getByIsOnline());
    }

    public DeliveryDriverResponseDTO toggleDriverOnlineStatus(Integer driverId, boolean isOnline){
        DeliveryDriver driver = driverRepository.getById(driverId);
        if(HelperUtils.isNull(driver)){
            throw new ObjectNotFoundException("Driver Not Found");
        }
        driver.setIsOnline(isOnline);
        driver.setUpdatedDate(new Date());
        driverRepository.save(driver);
        return DeliveryDriverResponseDTO.toResponse(driver);
    }

    public DeliveryDriverResponseDTO updateDriverLocation(Integer driverId , Double lat, Double lng){
        DeliveryDriver driver = driverRepository.getById(driverId);
        if(HelperUtils.isNull(driver)){
            throw new ObjectNotFoundException("Driver Not Found");
        }
        driver.setUpdatedDate(new Date());
        driver.setCurrentLat(lat);
        driver.setCurrentLng(lng);
        driverRepository.save(driver);
        return DeliveryDriverResponseDTO.toResponse(driver);
    }

    public List<DeliveryResponseDTO> driverDeliveryHistory(Integer id){
        DeliveryDriver driver = driverRepository.getById(id);
        if(HelperUtils.isNull(driver)){
            throw new ObjectNotFoundException("Driver Not Found");
        }
        return DeliveryResponseDTO.toResponse(driver.getDeliveries());
    }

    public List<DeliveryResponseDTO> getActiveDelivery(Integer driverId) {

        List<Delivery> delivery = deliveryRepository.getActiveDelivery(driverId);
        if (HelperUtils.isNull(delivery)) {
            throw new ObjectNotFoundException("No active delivery found.");
        }
        return DeliveryResponseDTO.toResponse(delivery);
    }

    public DeliveryResponseDTO assignDriverToOrder(Integer orderId, Integer driverId) {

        Order order = orderRepository.getById(orderId);
        if(HelperUtils.isNull(order)) {
           throw new OrderNotFoundException();
        }
        if(HelperUtils.isNotNull(order.getDelivery())){
            throw new InvalidRequestException("Delivery already assigned");
        }

        DeliveryDriver driver = driverRepository.getById(driverId);

        if (HelperUtils.isNull(driver)) {
            throw new ObjectNotFoundException("Driver Not Found");
        }

        if (!driver.getIsOnline()) {
            throw new InvalidRequestException("Driver is not Online");
        }

        Delivery delivery = new Delivery();
        delivery.setOrder(order);
        delivery.setStatus("ASSIGNED");
        delivery.setAssignedAt(new Date());
        delivery.setIsActive(true);
        delivery.setTrackingCode(HelperUtils.generateId("d-",4));
        order.setDelivery(delivery);
        order.setUpdatedDate(new Date());
        driver.getDeliveries().add(delivery);
        driver.setUpdatedDate(new Date());

        deliveryRepository.save(delivery);
        orderRepository.save(order);
        driverRepository.save(driver);

        return DeliveryResponseDTO.toResponse(delivery);
    }

    public DeliveryResponseDTO autoAssignDriver(Integer orderId){
        Order order = orderRepository.getById(orderId);

        if(HelperUtils.isNull(order)) {
            throw new OrderNotFoundException();
        }
        if(HelperUtils.isNotNull(order.getDelivery())){
            throw new InvalidRequestException("Delivery already assigned");
        }
        DeliveryDriver deliveryDriver = driverRepository.findFirstOnline();
        if(HelperUtils.isNull(deliveryDriver)){
            throw new ObjectNotFoundException("There is no driver.");
        }
        Delivery delivery = new Delivery();
        delivery.setAssignedAt(new Date());
        delivery.setIsActive(true);
        delivery.setOrder(order);
        delivery.setStatus("ASSIGNED");
        delivery.setTrackingCode(HelperUtils.generateId("d-",4));
        delivery.setDeliveryDriver(deliveryDriver);
        order.setDelivery(delivery);
        order.setUpdatedDate(new Date());
        deliveryDriver.getDeliveries().add(delivery);
        deliveryDriver.setUpdatedDate(new Date());
        deliveryRepository.save(delivery);
        orderRepository.save(order);
        driverRepository.save(deliveryDriver);
        return DeliveryResponseDTO.toResponse(delivery);

    }

    public DeliveryResponseDTO markDeliveryPickedUp(Integer deliveryId) {

        Delivery delivery = deliveryRepository.getByID(deliveryId);
        if(HelperUtils.isNull(delivery)) {
            throw new ObjectNotFoundException("Delivery not found");
        }

        delivery.setStatus("PICKED_UP");
        delivery.setPickedUpAt(new Date());
        Order order = delivery.getOrder();
        order.setStatus(OrderStatus.OUT_FOR_DELIVERY);
        orderRepository.save(order);

        OrderStatusHistory history = new OrderStatusHistory();
        history.setOrder(order);
        history.setStatus(OrderStatus.OUT_FOR_DELIVERY);
        history.setChangedAt(new Date());
        statusHistoryRepository.save(history);

        deliveryRepository.save(delivery);
        return DeliveryResponseDTO.toResponse(delivery);
    }

    public DeliveryResponseDTO markDeliveryDelivered(Integer deliveryId) {

        Delivery delivery = deliveryRepository.getByID(deliveryId);
        if(HelperUtils.isNull(delivery)) {
            throw new ObjectNotFoundException("Delivery not found");
        }

        if (!delivery.getStatus().equalsIgnoreCase("PICKED_UP")) {
            throw new InvalidRequestException("Only picked up deliveries can be marked as delivered.");
        }

        delivery.setStatus("DELIVERED");
        delivery.setDeliveredAt(new Date());
        Order order = delivery.getOrder();
        order.setStatus(OrderStatus.DELIVERED);
        orderRepository.save(order);

        OrderStatusHistory history = new OrderStatusHistory();
        history.setOrder(order);
        history.setStatus(OrderStatus.DELIVERED);
        history.setChangedAt(new Date());
        statusHistoryRepository.save(history);

        deliveryRepository.save(delivery);
        return DeliveryResponseDTO.toResponse(delivery);
    }

    public List<DeliveryResponseDTO> getDeliveriesForDriver(Integer driverId, String status) {

        List<Delivery> deliveries = deliveryRepository.getDeliveries(driverId , status);

        if(HelperUtils.isNull(deliveries)) {
            throw new ObjectNotFoundException("Delivery not found");
        }
        return DeliveryResponseDTO.toResponse(deliveries);
    }

    public List<DeliveryResponseDTO> getDeliveries(String status) {

        List<Delivery> deliveries = deliveryRepository.getDeliveries(status);

        if(HelperUtils.isNull(deliveries)) {
            throw new ObjectNotFoundException("Delivery not found");
        }
        return DeliveryResponseDTO.toResponse(deliveries);
    }

    public DeliveryResponseDTO getById(Integer id){
        Delivery delivery = deliveryRepository.getByID(id);
        if(HelperUtils.isNull(delivery)){
            throw new ObjectNotFoundException("Delivery not found.");
        }
        return DeliveryResponseDTO.toResponse(delivery);
    }

    public List<NearbyDriverDTO> getNearbyDrivers(Double lat, Double lng, Double radiusKm) {

        return driverRepository.findNearbyOnlineDrivers(lat, lng, radiusKm)
                .stream()
                .map(d -> new NearbyDriverDTO(
                        d.getId(),
                        d.getDriverCode(),
                        d.getVehicleType(),
                        d.getVehiclePlate(),
                        d.getCurrentLat(),
                        d.getCurrentLng()
                ))
                .toList();
    }

    public DriverPerformanceDTO getDriverPerformance(Integer driverId) {
        if (HelperUtils.isNull(driverRepository.getById(driverId))) {
            throw new ObjectNotFoundException("Driver not found: " + driverId);
        }

        Object[] stats = deliveryRepository.getDriverPerformanceStats(driverId);

        Integer completedCount = stats[0] != null ? ((Number) stats[0]).intValue() : 0;
        Double avgMinutes = stats[1] != null ? ((Number) stats[1]).doubleValue() : 0.0;

        return new DriverPerformanceDTO(driverId, completedCount, avgMinutes);
    }

    public String getDriverEarnings(Integer driverId, String from, String to) {
        DeliveryDriver driver = driverRepository.getById(driverId);
        if (HelperUtils.isNull(driver)) throw new ObjectNotFoundException("Driver not Found");

        Date fromDate = HelperUtils.parseDate(from);
        Date toDate   = HelperUtils.parseDate(to);

        List<Delivery> deliveries = deliveryRepository.findDeliveriesByDriverAndDateRange(
                driverId, fromDate, toDate);

        Double totalEarnings = deliveries.stream()
                .mapToDouble(d -> d.getOrder().getDeliveryFee())
                .sum();

        long avgMinutes = 0;
        List<Delivery> timed = deliveries.stream()
                .filter(d -> HelperUtils.isNotNull(d.getPickedUpAt())
                        && HelperUtils.isNotNull(d.getDeliveredAt()))
                .toList();

        if (!timed.isEmpty()) {
            avgMinutes = (long) timed.stream()
                    .mapToLong(d -> d.getDeliveredAt().getTime() - d.getPickedUpAt().getTime())
                    .average()
                    .orElse(0) / 60000;
        }

        return "Driver Earnings Report\n"
                + "Driver: " + driver.getDriverCode() + "\n"
                + "Period: " + from + " to " + to + "\n"
                + "Total Deliveries: " + deliveries.size() + "\n"
                + "Total Earnings: " + HelperUtils.formatCurrency(totalEarnings, "OMR") + "\n"
                + "Average Delivery Time: " + avgMinutes + " min";
    }




}
