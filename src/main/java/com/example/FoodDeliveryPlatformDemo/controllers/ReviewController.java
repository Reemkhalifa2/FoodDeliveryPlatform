package com.example.FoodDeliveryPlatformDemo.controllers;

import com.example.FoodDeliveryPlatformDemo.dto.response.ReviewResponseDTO;
import com.example.FoodDeliveryPlatformDemo.repositories.ReviewRepository;
import com.example.FoodDeliveryPlatformDemo.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("reviews")
public class ReviewController {

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    ReviewService reviewService;

    @PostMapping("/restaurant/{restaurantId}/customer/{customerId}")
    public ResponseEntity<ReviewResponseDTO> leaveRestaurantReview(@PathVariable Integer restaurantId,
                                                  @PathVariable Integer customerId,
                                                  @RequestParam String comment,
                                                  @RequestParam Integer rating
                                                  ){
        ReviewResponseDTO response = reviewService.leaveRestaurantReview(restaurantId,customerId,rating,comment);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }
    @PostMapping("/driver/{driverId}/customer/{customerId}")

    public ResponseEntity<ReviewResponseDTO> leaveDriverReview(@PathVariable Integer driverId,
                                                                   @PathVariable Integer customerId,
                                                                   @RequestParam String comment,
                                                                   @RequestParam Integer rating
    ){
        ReviewResponseDTO response = reviewService.leaveDriverReview(driverId,customerId,rating,comment);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<ReviewResponseDTO>> getRestaurantReviews(@PathVariable Integer restaurantId){
        return ResponseEntity.ok(reviewService.getRestaurantReviews(restaurantId));
    }

    @GetMapping("/driver/{driverId}")
    public ResponseEntity<List<ReviewResponseDTO>> getDriverReviews(@PathVariable Integer driverId){
        return ResponseEntity.ok(reviewService.getDriverReviews(driverId));
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<ReviewResponseDTO> deleteReview(@PathVariable Integer reviewId){

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(reviewService.deleteReview(reviewId));
    }

}
