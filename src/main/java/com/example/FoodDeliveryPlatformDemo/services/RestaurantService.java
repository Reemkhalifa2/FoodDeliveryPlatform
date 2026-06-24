package com.example.FoodDeliveryPlatformDemo.services;

import com.example.FoodDeliveryPlatformDemo.dto.request.MenuItemRequestDTO;
import com.example.FoodDeliveryPlatformDemo.dto.request.RestaurantOwnerRequestDTO;
import com.example.FoodDeliveryPlatformDemo.dto.request.RestaurantRequestDTO;
import com.example.FoodDeliveryPlatformDemo.dto.response.ComboMealResponseDTO;
import com.example.FoodDeliveryPlatformDemo.dto.response.MenuItemResponseDTO;
import com.example.FoodDeliveryPlatformDemo.dto.response.RestaurantOwnerResponseDTO;
import com.example.FoodDeliveryPlatformDemo.dto.response.RestaurantResponseDTO;
import com.example.FoodDeliveryPlatformDemo.entities.MenuItem;
import com.example.FoodDeliveryPlatformDemo.entities.Restaurant;
import com.example.FoodDeliveryPlatformDemo.entities.RestaurantOwner;
import com.example.FoodDeliveryPlatformDemo.exceptions.NullRequestBodyException;
import com.example.FoodDeliveryPlatformDemo.exceptions.OwnerNotFoundException;
import com.example.FoodDeliveryPlatformDemo.exceptions.RestaurantNotFoundException;
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
    public RestaurantService(RestaurantOwnerRepository restaurantOwnerRepository, RestaurantRepository restaurantRepository , MenuItemRepository menuItemRepository) {
        this.restaurantOwnerRepository = restaurantOwnerRepository;
        this.restaurantRepository = restaurantRepository;
        this.menuItemRepository = menuItemRepository;
    }

    RestaurantRepository restaurantRepository;
    RestaurantOwnerRepository restaurantOwnerRepository;
    MenuItemRepository menuItemRepository;


    public RestaurantOwnerResponseDTO addRestaurantOwner(RestaurantOwnerRequestDTO restaurantOwnerRequestDTO){
        if(HelperUtils.isNull(restaurantOwnerRequestDTO)){
            throw new NullRequestBodyException("Request body is null");
        }
        RestaurantOwner restaurantOwner = RestaurantOwnerRequestDTO.toEntity(restaurantOwnerRequestDTO);
        restaurantOwner.setIsActive(true);
        restaurantOwner.setCreatedDate(new Date());
        restaurantOwnerRepository.save(restaurantOwner);
        return RestaurantOwnerResponseDTO.toResponse(restaurantOwner);
    }

    public RestaurantResponseDTO createRestaurant(RestaurantRequestDTO dto, Integer ownerId){
        RestaurantOwner restaurantOwner = restaurantOwnerRepository.getById(ownerId);
        if(HelperUtils.isNull(restaurantOwner)){
            throw new OwnerNotFoundException();
        }
        if(HelperUtils.isNull(dto)){
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
    public RestaurantResponseDTO getById(Integer id){
        Restaurant restaurant = restaurantRepository.getById(id);
        if(HelperUtils.isNull(restaurant)){
            throw new RestaurantNotFoundException();
        }
        return RestaurantResponseDTO.toResponse(restaurant);
    }
    public List<RestaurantResponseDTO> getByCuisine(String cuisine){
        return RestaurantResponseDTO.toResponse(restaurantRepository.findByCuisineTypeIgnoreCase(cuisine));
    }

    public List<RestaurantResponseDTO> getAll(){
        return RestaurantResponseDTO.toResponse(restaurantRepository.getAll());
    }

    public RestaurantResponseDTO acceptOrder(Integer id , Boolean accepting){
        Restaurant restaurant = restaurantRepository.getById(id);
        restaurant.setAcceptingOrders(accepting);
        restaurant.setUpdatedDate(new Date());
        restaurantRepository.save(restaurant);
        return RestaurantResponseDTO.toResponse(restaurant);
    }

    public RestaurantResponseDTO updateDeliveryFee(Integer id , Double newFee){
        Restaurant restaurant = restaurantRepository.getById(id);
        restaurant.setDeliveryFee(newFee);
        restaurant.setUpdatedDate(new Date());
        restaurantRepository.save(restaurant);
        return RestaurantResponseDTO.toResponse(restaurant);
    }

    public List<MenuItemResponseDTO> getAllMenuItems(Integer restaurantId){
        return MenuItemResponseDTO.toResponse(restaurantRepository.findMenuByRestaurantId(restaurantId));
    }

    public List<ComboMealResponseDTO> getAllComboMeal(Integer restaurantId){
        return ComboMealResponseDTO.toResponse(restaurantRepository.findComboMealByRestaurantId(restaurantId));
    }

    public MenuItemResponseDTO addMenuItem(Integer id, MenuItemRequestDTO dto){
        Restaurant restaurant = restaurantRepository.getById(id);
        if(HelperUtils.isNull(dto)){
            throw new NullRequestBodyException("Request Body id null");
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



}
