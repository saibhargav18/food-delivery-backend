package com.fooddelivery.food_delivery_backend.dto.request.review;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateReviewRequest {

    @NotNull(message = "Order Id is required")
    @Positive
    private Long orderId;

    @NotNull
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must be at most 5")
    @Positive
    private Integer rating;

    @Size(max=500)
    private String comment;


}
