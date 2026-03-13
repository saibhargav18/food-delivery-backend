package com.fooddelivery.food_delivery_backend.dto.request.menu;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateMenuItemRequest {


    @Size(min = 2,max = 50,message = "Name must be between 2 and 50 characters")
    private String name;

    private String description;


    @DecimalMin(value = "0.01",message = "Price must be at least 0.01")
    private BigDecimal price;

    private String imageUrl;

    private Boolean isVeg;


    @Positive(message = "Category ID must be positive")
    private Long categoryId;
}
