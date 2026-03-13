package com.fooddelivery.food_delivery_backend.dto.response.menu;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryResponse {

    private long id;
    private String name;
    private String description;
    private long restaurantId;
}
