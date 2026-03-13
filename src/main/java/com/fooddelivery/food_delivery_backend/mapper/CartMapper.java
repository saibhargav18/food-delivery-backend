package com.fooddelivery.food_delivery_backend.mapper;

import com.fooddelivery.food_delivery_backend.dto.request.cart.AddToCartRequest;
import com.fooddelivery.food_delivery_backend.dto.request.cart.UpdateCartItemRequest;
import com.fooddelivery.food_delivery_backend.dto.response.cart.CartItemResponse;
import com.fooddelivery.food_delivery_backend.dto.response.cart.CartResponse;
import com.fooddelivery.food_delivery_backend.entities.Cart;
import com.fooddelivery.food_delivery_backend.entities.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CartMapper {

    //ENTITY -> RESPONSE
    @Mapping(source = "restaurant.id",target = "restaurantId")
    @Mapping(source = "restaurant.name",target = "restaurantName")
    @Mapping(target = "items",ignore = true)
    CartResponse toResponse(Cart cart);



    /*
    @Mapping(target = "id",ignore = true)
    @Mapping(target = "totalAmount",constant = "0")
    @Mapping(target = "isActive",constant = "true")
    @Mapping(target = "menuItem",ignore = true)
    Cart toEntity(AddToCartRequest request);

    @Mapping(target = "id",ignore = true)
    @Mapping(target = "cartItemId",ignore = true)
    @Mapping(target = "cart",ignore = true)
    @Mapping(target = "menuItem",ignore = true)
    void updateCartItem(UpdateCartItemRequest request, @MappingTarget CartItem cartItem);


     */
}
