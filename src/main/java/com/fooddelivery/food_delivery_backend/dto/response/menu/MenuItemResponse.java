package com.fooddelivery.food_delivery_backend.dto.response.menu;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MenuItemResponse {

    private long id;
    private String name;
    private String description;
    private BigDecimal price;
    private String imageUrl;
    private Boolean isVeg;
    private Boolean isAvailable;
    private long restaurantId;
    private long categoryId;

}
