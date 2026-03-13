package com.fooddelivery.food_delivery_backend.mapper;

import com.fooddelivery.food_delivery_backend.dto.request.restaurant.CreateRestaurantRequest;
import com.fooddelivery.food_delivery_backend.dto.request.restaurant.UpdateRestaurantRequest;
import com.fooddelivery.food_delivery_backend.dto.response.restaurant.RestaurantDetailResponse;
import com.fooddelivery.food_delivery_backend.dto.response.restaurant.RestaurantResponse;
import com.fooddelivery.food_delivery_backend.entities.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RestaurantMapper {

    //ENTITY->RESPONSE
    @Mapping(source = "owner.id",target = "ownerId")
    RestaurantResponse toResponse(Restaurant restaurant);


    //REQUEST->ENTITY
    @Mapping(target = "id",ignore = true)
    @Mapping(target = "isActive",constant = "true")
    @Mapping(target = "isAcceptingOrders",constant = "true")
    @Mapping(target = "averageRating",constant = "0.0")
    @Mapping(target = "totalReviews",constant = "0")
    @Mapping(target = "owner",ignore = true)
    Restaurant toEntity(CreateRestaurantRequest request);


    //UPDATE REQUEST -> ENTITY

    @Mapping(target = "id",ignore = true)
    @Mapping(target = "owner",ignore = true)
    @Mapping(target = "averageRating", ignore = true)  // Can't manually update
    @Mapping(target = "totalReviews", ignore = true)
    Restaurant updateRestaurantRequest(UpdateRestaurantRequest request, @MappingTarget Restaurant restaurant);


}
