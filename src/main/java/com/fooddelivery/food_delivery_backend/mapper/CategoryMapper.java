package com.fooddelivery.food_delivery_backend.mapper;

import com.fooddelivery.food_delivery_backend.dto.request.menu.CreateCategoryRequest;
import com.fooddelivery.food_delivery_backend.dto.response.menu.CategoryResponse;
import com.fooddelivery.food_delivery_backend.entities.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    //Entity to Response
    @Mapping(source = "restaurant.id",target = "restaurantId")
    CategoryResponse toResponse(Category category);

    // Request DTO → Entity
    @Mapping(target = "id",ignore = true)
    @Mapping(target = "restaurant",ignore = true)
    Category toEntity(CreateCategoryRequest request);
}
