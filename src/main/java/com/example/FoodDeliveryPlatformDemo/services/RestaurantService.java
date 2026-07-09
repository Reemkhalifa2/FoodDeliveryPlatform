package com.example.FoodDeliveryPlatformDemo.services;

import com.example.FoodDeliveryPlatformDemo.dto.request.ComboMealRequestDTO;
import com.example.FoodDeliveryPlatformDemo.dto.request.MenuItemRequestDTO;
import com.example.FoodDeliveryPlatformDemo.dto.request.RestaurantOwnerRequestDTO;
import com.example.FoodDeliveryPlatformDemo.dto.request.RestaurantRequestDTO;
import com.example.FoodDeliveryPlatformDemo.dto.response.*;
import com.example.FoodDeliveryPlatformDemo.entities.*;
import com.example.FoodDeliveryPlatformDemo.enums.OrderStatus;
import com.example.FoodDeliveryPlatformDemo.exceptions.*;
import com.example.FoodDeliveryPlatformDemo.repositories.*;
import com.example.FoodDeliveryPlatformDemo.utilities.HelperUtils;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.w3c.dom.ls.LSInput;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class RestaurantService {

    @Autowired
    public RestaurantService(RestaurantOwnerRepository restaurantOwnerRepository,
                             OrderRepository orderRepository,
                             ReviewRepository reviewRepository,
                             RestaurantRepository restaurantRepository, MenuItemRepository menuItemRepository, ComboMealRepository comboMealRepository) {
        this.restaurantOwnerRepository = restaurantOwnerRepository;
        this.restaurantRepository = restaurantRepository;
        this.menuItemRepository = menuItemRepository;
        this.comboMealRepository = comboMealRepository;
        this.orderRepository = orderRepository;
        this.reviewRepository = reviewRepository;
    }

    RestaurantRepository restaurantRepository;
    RestaurantOwnerRepository restaurantOwnerRepository;
    MenuItemRepository menuItemRepository;
    ComboMealRepository comboMealRepository;
    OrderRepository orderRepository;
    ReviewRepository reviewRepository;


    public List<MenuItemResponseDTO> searchMenuItems(
            Integer restaurantId,
            String name,
            Double minCalories,
            Double maxCalories) {

        return MenuItemResponseDTO.toResponse(menuItemRepository.searchMenuItems(restaurantId,name, minCalories, maxCalories));
    }

    public String analytics(Integer id){
        if(HelperUtils.isNull(restaurantRepository.getById(id))){throw new RestaurantNotFoundException();}
        if(HelperUtils.isNull(reviewRepository.findByRestaurantId(id))){throw new ObjectNotFoundException("No Review for this restaurant");}
        Double avgRating =  reviewRepository.findRatingByRestaurantId(id);
        if(HelperUtils.isNull(orderRepository.findByRestaurantId(id))){throw new ObjectNotFoundException("No revenue for this restaurant");}
        Double revenue = orderRepository.sumDeliveredOrders(id);
        Integer completedOrders = orderRepository.countCompletedOrders(id);

        StringBuilder sb = new StringBuilder();
        sb.append("Restaurant Analytics\n");
        sb.append("Restaurant ID: ").append(id).append("\n");
        sb.append("Average Rating: ").append(avgRating).append("\n");
        sb.append("Total Revenue: ").append(revenue).append("\n");
        sb.append("Completed Orders: ").append(completedOrders).append("\n");

        return sb.toString();

    }



    public RestaurantOwnerResponseDTO addRestaurantOwner(RestaurantOwnerRequestDTO restaurantOwnerRequestDTO) {
        if (HelperUtils.isNull(restaurantOwnerRequestDTO)) {
            throw new NullRequestBodyException("Request body is null");
        }
        RestaurantOwner restaurantOwner = RestaurantOwnerRequestDTO.toEntity(restaurantOwnerRequestDTO);
        if (restaurantOwnerRepository.existsByEmail(restaurantOwner.getEmail())) {
            throw new InvalidRequestException("Email already exists");
        }
        restaurantOwner.setIsActive(true);
        restaurantOwner.setCreatedDate(new Date());
        restaurantOwnerRepository.save(restaurantOwner);
        return RestaurantOwnerResponseDTO.toResponse(restaurantOwner);
    }

    public RestaurantResponseDTO createRestaurant(RestaurantRequestDTO dto, Integer ownerId) {
        RestaurantOwner restaurantOwner = restaurantOwnerRepository.getById(ownerId);
        if (HelperUtils.isNull(restaurantOwner)) {
            throw new OwnerNotFoundException();
        }
        if (HelperUtils.isNull(dto)) {
            throw new NullRequestBodyException("Request body is null");
        }
        Restaurant restaurant = RestaurantRequestDTO.toEntity(dto);
        restaurant.setIsActive(true);
        restaurant.setCreatedDate(new Date());
        restaurant.setRestaurantOwner(restaurantOwner);
        restaurantOwner.getRestaurants().add(restaurant);
        restaurantRepository.save(restaurant);
        restaurantOwnerRepository.save(restaurantOwner);
        return RestaurantResponseDTO.toResponse(restaurant);

    }

    public RestaurantResponseDTO getById(Integer id) {
        Restaurant restaurant = restaurantRepository.getById(id);
        if (HelperUtils.isNull(restaurant)) {
            throw new RestaurantNotFoundException();
        }
        return RestaurantResponseDTO.toResponse(restaurant);
    }

    public List<RestaurantResponseDTO> getRestaurantsByCuisine(String cuisine) {
        return RestaurantResponseDTO.toResponse(restaurantRepository.findByCuisineTypeIgnoreCase(cuisine));
    }

    public List<RestaurantResponseDTO> getRestaurantsUnderDeliveryFee(Double maxFee) {

        if (maxFee == null || maxFee < 0) {
            throw new IllegalArgumentException("Invalid delivery fee");
        }

        List<Restaurant> restaurants =
                restaurantRepository.findByDeliveryFeeLessThanEqual(maxFee);

        return RestaurantResponseDTO.toResponse(restaurants);
    }


    public List<RestaurantResponseDTO> getAll() {
        return RestaurantResponseDTO.toResponse(restaurantRepository.getAll());
    }

    public RestaurantResponseDTO toggleAcceptingOrders(Integer id, Boolean status) {
        Restaurant restaurant = restaurantRepository.getById(id);
        restaurant.setAcceptingOrders(status);
        restaurant.setUpdatedDate(new Date());
        restaurantRepository.save(restaurant);
        return RestaurantResponseDTO.toResponse(restaurant);
    }

    public RestaurantResponseDTO updateDeliveryFee(Integer restaurantId, Double newFee) {
        Restaurant restaurant = restaurantRepository.getById(restaurantId);
        restaurant.setDeliveryFee(newFee);
        restaurant.setUpdatedDate(new Date());
        restaurantRepository.save(restaurant);
        return RestaurantResponseDTO.toResponse(restaurant);
    }

    public List<MenuItemResponseDTO> getMenuForRestaurant(Integer restaurantId) {
        return MenuItemResponseDTO.toResponse(menuItemRepository.findByRestaurantId(restaurantId));
    }

    public List<ComboMealResponseDTO> getAllComboMeal(Integer restaurantId) {
        return ComboMealResponseDTO.toResponse(restaurantRepository.findComboMealByRestaurantId(restaurantId));
    }

    public MenuItemResponseDTO addMenuItem(Integer id, MenuItemRequestDTO dto) {
        Restaurant restaurant = restaurantRepository.getById(id);

        if (HelperUtils.isNull(dto)) {
            throw new NullRequestBodyException("Request Body id null");
        }
        if (HelperUtils.isNull(restaurant)) {
            throw new RestaurantNotFoundException();
        }

        MenuItem menuItem = MenuItemRequestDTO.toEntity(dto);
        restaurant.getMenuItems().add(menuItem);
        restaurant.setUpdatedDate(new Date());
        menuItem.setIsActive(true);
        menuItem.setCreatedDate(new Date());
        menuItem.setRestaurant(restaurant);
        menuItemRepository.save(menuItem);
        restaurantRepository.save(restaurant);
        return MenuItemResponseDTO.toResponse(menuItem);

    }


    public MenuItemResponseDTO updateAvailability(Integer itemId, Boolean status) {

        MenuItem menuItem = menuItemRepository.getById(itemId);

        if (HelperUtils.isNull(menuItem)) {
            throw new MenuItemNotFoundException("Request Body is null.");
        }

        menuItem.setIsAvailable(status);
        menuItem.setUpdatedDate(new Date());

        menuItemRepository.save(menuItem);
        return MenuItemResponseDTO.toResponse(menuItem);
    }

    public ComboMealResponseDTO addComboMeal(Integer restaurantId, ComboMealRequestDTO dto) {

        if (HelperUtils.isNull(dto)) {
            throw new NullRequestBodyException("Request body is null");
        }

        Restaurant restaurant = restaurantRepository.getById(restaurantId);

        if (HelperUtils.isNull(restaurant)) {
            throw new RestaurantNotFoundException();
        }

        ComboMeal comboMeal = ComboMealRequestDTO.toEntity(dto);

        comboMeal.setRestaurant(restaurant);
        comboMeal.setIsActive(true);
        comboMeal.setCreatedDate(new Date());

        restaurant.getComboMeals().add(comboMeal);
        restaurant.setUpdatedDate(new Date());
        comboMealRepository.save(comboMeal);
        restaurantRepository.save(restaurant);

        return ComboMealResponseDTO.toResponse(comboMeal);
    }

    public List<MenuItemResponseDTO> bulkUpdateMenuItemPrices(Integer restaurantId, Double percentageIncrease) {

        Restaurant restaurant = restaurantRepository.getById(restaurantId);

        if (HelperUtils.isNull(restaurant)) {
            throw new RestaurantNotFoundException();
        }

        if (percentageIncrease <= 0) {
            throw new IllegalArgumentException("Percentage must be greater than 0");
        }

        List<MenuItem> menuItems = restaurant.getMenuItems();

        if (HelperUtils.isNotNull(menuItems)) {
            for (MenuItem item : menuItems) {
                Double newPrice = item.getPrice() + (item.getPrice() * percentageIncrease / 100);
                item.setPrice(newPrice);
                item.setUpdatedDate(new Date());
            }
        }


        menuItemRepository.saveAll(menuItems);
        restaurant.setUpdatedDate(new Date());
        restaurantRepository.save(restaurant);
        return MenuItemResponseDTO.toResponse(menuItems);
    }

    public Double getRestaurantRevenue(Integer restaurantId, String dateStr) {
        Restaurant restaurant = restaurantRepository.getById(restaurantId);

        if (HelperUtils.isNull(restaurant)) {
            throw new RestaurantNotFoundException();
        }

        Date date;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format, expected yyyy-MM-dd (e.g. 2026-06-27)");
        }

        Date nextDay = Date.from(
                date.toInstant().plus(1, ChronoUnit.DAYS)
        );

        List<Order> orders = orderRepository.findByOrderDateBetween(restaurantId, date, nextDay);
        Double revenue = 0.0;
        for (Order order : orders) {

                revenue += order.getTotalAmount();

        }

        return revenue;
    }

    public Integer totalLifetimeOrders(Integer restaurantId){
        if(HelperUtils.isNull(restaurantRepository.getById(restaurantId))){
            throw new RestaurantNotFoundException();
        }
        List<Order> orders = orderRepository.findByRestaurantId(restaurantId);
        return orders.size();

    }

    public List<Map<String, Object>> getTopSellers(Integer restaurantId) {
        if(HelperUtils.isNull(restaurantRepository.getById(restaurantId))){
            throw new RestaurantNotFoundException();
        }

        List<Object[]> results = menuItemRepository.findTopSellingByQuantity(restaurantId);
        List<Map<String, Object>> topSellers = new ArrayList<>();
        int rank = 1;
        for (Object[] row : results) {
            MenuItem item      = (MenuItem) row[0];  // oi.menuItem
            Long totalQuantity = (Long)     row[1];  // SUM(oi.quantity)
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("rank",              rank++);
            map.put("menuItemId",        item.getId());
            map.put("totalQuantitySold", totalQuantity);
            topSellers.add(map);
        }

        return topSellers;
    }


    public String getRestaurantRevenue(Integer restaurantId, String from, String to) {
        Restaurant restaurant = restaurantRepository.getById(restaurantId);
        if (HelperUtils.isNull(restaurant)) throw new RestaurantNotFoundException();

        Date fromDate = HelperUtils.parseDate(from);
        Date toDate   = HelperUtils.parseDate(to);

        List<Order> orders = orderRepository.findByFilters(
                restaurantId, fromDate, toDate);

        Double totalRevenue = orders.stream()
                .mapToDouble(Order::getTotalAmount)
                .sum();

        Double totalFees = orders.stream()
                .mapToDouble(Order::getDeliveryFee)
                .sum();

        return "Restaurant Revenue Report\n"
                + "Restaurant: " + restaurant.getName() + "\n"
                + "Period: " + from + " to " + to + "\n"
                + "Total Orders: " + orders.size() + "\n"
                + "Total Revenue: " + HelperUtils.formatCurrency(totalRevenue, "OMR") + "\n"
                + "Total Delivery Fees: " + HelperUtils.formatCurrency(totalFees, "OMR") + "\n"
                + "Net Revenue: " + HelperUtils.formatCurrency(totalRevenue - totalFees, "OMR");
    }

    public Page<RestaurantResponseDTO> searchRestaurants(
            String keyword,
            int page,
            int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<Restaurant> restaurants =
                restaurantRepository.search(keyword, pageable);

        return restaurants.map(RestaurantResponseDTO::toResponse);
    }





}
