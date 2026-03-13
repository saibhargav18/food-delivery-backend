package com.fooddelivery.food_delivery_backend.dto.request.menu;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateCategoryRequest {

    @NotBlank(message = "Name is required")
    @Size(min = 2,max = 50,message = "Name must be between 2 and 50 characters")
    private String name;

    private String description;

    @NotNull(message = "Restaurant Id is required")
    @Positive(message = "Restaurant ID must be positive")
    private Long restaurantId;


}
