package com.fooddelivery.food_delivery_backend.mapper;

import com.fooddelivery.food_delivery_backend.dto.request.menu.CreateMenuItemRequest;
import com.fooddelivery.food_delivery_backend.dto.request.menu.UpdateMenuItemRequest;
import com.fooddelivery.food_delivery_backend.dto.response.menu.MenuItemResponse;
import com.fooddelivery.food_delivery_backend.entities.MenuItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "Spring")
public interface MenuItemMapper {

    //Entity to Response
    @Mapping(source = "restaurant.id",target = "restaurantId")
    @Mapping(source = "category.id",target = "categoryId")
    MenuItemResponse toResponse(MenuItem menuItem);


    //Request to Entity
    @Mapping(target = "id",ignore = true)
    @Mapping(target = "restaurant",ignore = true)
    @Mapping(target = "category",ignore = true)
    @Mapping(target = "isAvailable", constant = "true")
    MenuItem toEntity(CreateMenuItemRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "restaurant", ignore = true)  // Can't change restaurant
    @Mapping(target = "category", ignore = true)
    MenuItem updateMenuItem(UpdateMenuItemRequest request, @MappingTarget MenuItem menuItem);
}
