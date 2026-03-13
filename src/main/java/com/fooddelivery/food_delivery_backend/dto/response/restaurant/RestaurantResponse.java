package com.fooddelivery.food_delivery_backend.dto.response.restaurant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RestaurantResponse {

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




    }

