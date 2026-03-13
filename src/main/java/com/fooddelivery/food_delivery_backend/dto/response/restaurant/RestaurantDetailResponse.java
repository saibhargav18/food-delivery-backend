package com.fooddelivery.food_delivery_backend.dto.response.restaurant;

import com.fooddelivery.food_delivery_backend.dto.response.menu.CategoryResponse;
import com.fooddelivery.food_delivery_backend.dto.response.menu.MenuItemResponse;
import com.fooddelivery.food_delivery_backend.dto.response.review.ReviewResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RestaurantDetailResponse {

    private long id;
    private String name;
    private String description;
    private String contact;
    private String email;
    private String street;
    private String city;
    private String state;
    private String pincode;
    private LocalTime openingTime;
    private LocalTime closingTime;
    private Boolean isActive;
    private Boolean isAcceptingOrders;
    private Double averageRating;
    private Integer totalReviews;
    private long ownerId;
    private String ownerName;
    private List<MenuItemResponse> menuItems;
    private List<CategoryResponse> categoryResponses;
    private List<ReviewResponse> reviews;
}
