package com.example.FoodDeliveryPlatformDemo.services;

import com.example.FoodDeliveryPlatformDemo.dto.response.ReviewResponseDTO;
import com.example.FoodDeliveryPlatformDemo.entities.Customer;
import com.example.FoodDeliveryPlatformDemo.entities.DeliveryDriver;
import com.example.FoodDeliveryPlatformDemo.entities.Restaurant;
import com.example.FoodDeliveryPlatformDemo.entities.Review;
import com.example.FoodDeliveryPlatformDemo.exceptions.CustomerNotFoundException;
import com.example.FoodDeliveryPlatformDemo.exceptions.ObjectNotFoundException;
import com.example.FoodDeliveryPlatformDemo.exceptions.RestaurantNotFoundException;
import com.example.FoodDeliveryPlatformDemo.repositories.CustomerRepository;
import com.example.FoodDeliveryPlatformDemo.repositories.DriverRepository;
import com.example.FoodDeliveryPlatformDemo.repositories.RestaurantRepository;
import com.example.FoodDeliveryPlatformDemo.repositories.ReviewRepository;
import com.example.FoodDeliveryPlatformDemo.utilities.HelperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ReviewService {

    @Autowired
    public ReviewService(CustomerRepository customerRepository,
                         DriverRepository driverRepository,
                         RestaurantRepository restaurantRepository, ReviewRepository reviewRepository) {
        this.customerRepository = customerRepository;
        this.reviewRepository = reviewRepository;
        this.restaurantRepository = restaurantRepository;
        this.driverRepository = driverRepository;
    }

    CustomerRepository customerRepository;
    RestaurantRepository restaurantRepository;
    ReviewRepository reviewRepository;
    DriverRepository driverRepository;

    public ReviewResponseDTO leaveRestaurantReview(Integer customerId, Integer restaurantId, int rating, String
            comment) {
        Customer customer = customerRepository.getById(customerId);
        if (HelperUtils.isNull(customer)) {
            throw new CustomerNotFoundException();
        }
        Restaurant restaurant = restaurantRepository.getById(restaurantId);
        if (HelperUtils.isNull(restaurant)) {
            throw new RestaurantNotFoundException();
        }
        Review review = new Review();
        review.setRestaurant(restaurant);
        review.setCustomer(customer);
        review.setIsActive(true);
        review.setRating(rating);
        review.setComment(comment);
        reviewRepository.save(review);
        customer.getReviews().add(review);
        customer.setUpdatedDate(new Date());
        customerRepository.save(customer);
        return ReviewResponseDTO.toResponse(review);
    }


    public ReviewResponseDTO leaveDriverReview(Integer customerId, Integer driverId, int rating, String
            comment) {
        Customer customer = customerRepository.getById(customerId);
        if (HelperUtils.isNull(customer)) {
            throw new CustomerNotFoundException();
        }
        DeliveryDriver deliveryDriver = driverRepository.getById(driverId);
        if (HelperUtils.isNull(deliveryDriver)) {
            throw new ObjectNotFoundException("Driver not found");
        }
        Review review = new Review();
        review.setDeliveryDriver(deliveryDriver);
        review.setCustomer(customer);
        review.setIsActive(true);
        review.setRating(rating);
        review.setComment(comment);
        reviewRepository.save(review);
        customer.getReviews().add(review);
        customer.setUpdatedDate(new Date());
        customerRepository.save(customer);
        return ReviewResponseDTO.toResponse(review);
    }

    public List<ReviewResponseDTO> getRestaurantReviews(Integer restaurantId) {
        if(HelperUtils.isNull(restaurantRepository.getById(restaurantId)))throw new RestaurantNotFoundException();
        return ReviewResponseDTO.toResponse(reviewRepository.findByRestaurantId(restaurantId));
    }

    public List<ReviewResponseDTO> getDriverReviews(Integer driverId) {
        if(HelperUtils.isNull(restaurantRepository.getById(driverId)))throw new ObjectNotFoundException("Driver Not Found");
        return ReviewResponseDTO.toResponse(reviewRepository.findByDriverId(driverId));
    }

    public ReviewResponseDTO deleteReview(Integer reviewId) {
        Review review = reviewRepository.getById(reviewId);
        if (HelperUtils.isNull(review)) {
            throw new ObjectNotFoundException("review not found");
        }
        review.setIsActive(false);
        review.setUpdatedDate(new Date());
        reviewRepository.save(review);
        return ReviewResponseDTO.toResponse(review);
    }

    public Double getRestaurantAverageRating(Integer id) {
        if (HelperUtils.isNull(restaurantRepository.getById(id))) {
            throw new RestaurantNotFoundException();
        }
        return reviewRepository.findRatingByRestaurantId(id);
    }

    public Double getDriverAverageRating(Integer id) {
        if (HelperUtils.isNull(driverRepository.getById(id))) {
            throw new ObjectNotFoundException("Driver not found");
        }
        return reviewRepository.findRatingByDriverId(id);
    }

    public Page<Review> findByRestaurantId(Integer id, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return reviewRepository.findByRestaurantId(id, pageable);
    }

    public Double restaurantAverageRating(Integer restaurantId){
        if (HelperUtils.isNull(restaurantRepository.getById(restaurantId))) {
            throw new RestaurantNotFoundException();
        }
        return reviewRepository.restaurantAverageRating(restaurantId);
    }

    public Double driverAverageRating(Integer driverId){
        if (HelperUtils.isNull(driverRepository.getById(driverId))) {
            throw new ObjectNotFoundException("Driver not found");
        }
        return reviewRepository.restaurantAverageRating(driverId);
    }



}
