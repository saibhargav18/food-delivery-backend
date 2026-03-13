package com.fooddelivery.food_delivery_backend.mapper;

import com.fooddelivery.food_delivery_backend.dto.request.review.CreateReviewRequest;
import com.fooddelivery.food_delivery_backend.dto.response.review.ReviewResponse;
import com.fooddelivery.food_delivery_backend.entities.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    @Mapping(source = "user.id",target = "userId")
    @Mapping(source = "user.name",target ="userName")
    @Mapping(source = "restaurant.id",target = "restaurantId")
    @Mapping(source = "order.id",target = "orderId")
    ReviewResponse toResponse(Review review);


    @Mapping(target = "id",ignore = true)
    @Mapping(target = "order",ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "restaurant", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Review toEntity(CreateReviewRequest request);
}
