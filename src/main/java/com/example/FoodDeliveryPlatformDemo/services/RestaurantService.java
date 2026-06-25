package com.example.FoodDeliveryPlatformDemo.services;

import com.example.FoodDeliveryPlatformDemo.dto.request.ComboMealRequestDTO;
import com.example.FoodDeliveryPlatformDemo.dto.request.MenuItemRequestDTO;
import com.example.FoodDeliveryPlatformDemo.dto.request.RestaurantOwnerRequestDTO;
import com.example.FoodDeliveryPlatformDemo.dto.request.RestaurantRequestDTO;
import com.example.FoodDeliveryPlatformDemo.dto.response.ComboMealResponseDTO;
import com.example.FoodDeliveryPlatformDemo.dto.response.MenuItemResponseDTO;
import com.example.FoodDeliveryPlatformDemo.dto.response.RestaurantOwnerResponseDTO;
import com.example.FoodDeliveryPlatformDemo.dto.response.RestaurantResponseDTO;
import com.example.FoodDeliveryPlatformDemo.entities.ComboMeal;
import com.example.FoodDeliveryPlatformDemo.entities.MenuItem;
import com.example.FoodDeliveryPlatformDemo.entities.Restaurant;
import com.example.FoodDeliveryPlatformDemo.entities.RestaurantOwner;
import com.example.FoodDeliveryPlatformDemo.exceptions.MenuItemNotFoundException;
import com.example.FoodDeliveryPlatformDemo.exceptions.NullRequestBodyException;
import com.example.FoodDeliveryPlatformDemo.exceptions.OwnerNotFoundException;
import com.example.FoodDeliveryPlatformDemo.exceptions.RestaurantNotFoundException;
import com.example.FoodDeliveryPlatformDemo.repositories.ComboMealRepository;
import com.example.FoodDeliveryPlatformDemo.repositories.MenuItemRepository;
import com.example.FoodDeliveryPlatformDemo.repositories.RestaurantOwnerRepository;
import com.example.FoodDeliveryPlatformDemo.repositories.RestaurantRepository;
import com.example.FoodDeliveryPlatformDemo.utilities.HelperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class RestaurantService {

    @Autowired
    public RestaurantService(RestaurantOwnerRepository restaurantOwnerRepository, RestaurantRepository restaurantRepository, MenuItemRepository menuItemRepository, ComboMealRepository comboMealRepository) {
        this.restaurantOwnerRepository = restaurantOwnerRepository;
        this.restaurantRepository = restaurantRepository;
        this.menuItemRepository = menuItemRepository;
        this.comboMealRepository = comboMealRepository;
    }

    RestaurantRepository restaurantRepository;
    RestaurantOwnerRepository restaurantOwnerRepository;
    MenuItemRepository menuItemRepository;
    ComboMealRepository comboMealRepository;


    public RestaurantOwnerResponseDTO addRestaurantOwner(RestaurantOwnerRequestDTO restaurantOwnerRequestDTO) {
        if (HelperUtils.isNull(restaurantOwnerRequestDTO)) {
            throw new NullRequestBodyException("Request body is null");
        }
        RestaurantOwner restaurantOwner = RestaurantOwnerRequestDTO.toEntity(restaurantOwnerRequestDTO);
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
        return MenuItemResponseDTO.toResponse(restaurantRepository.findMenuByRestaurantId(restaurantId));
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


}
