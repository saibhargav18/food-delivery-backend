package com.fooddelivery.food_delivery_backend.dto.response.review;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewResponse {

    private long id;
    private long userId;
    private String userName;
    private long restaurantId;
    private long orderId;
    private Integer rating;
    private String comment;
    private LocalDateTime createdAt;
}
