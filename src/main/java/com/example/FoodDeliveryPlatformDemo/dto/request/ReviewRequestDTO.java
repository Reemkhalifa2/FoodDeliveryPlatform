package com.example.FoodDeliveryPlatformDemo.dto.request;

import com.example.FoodDeliveryPlatformDemo.entities.Review;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class ReviewRequestDTO {
    @NotNull
    @Max(value = 5)
    @Min(value = 5)
    private Integer rating;
    private String comment;

    public static Review toEntity(ReviewRequestDTO dto) {
        Review review = new Review();

        review.setRating(dto.getRating());
        review.setComment(dto.getComment());

        return review;
    }

}
