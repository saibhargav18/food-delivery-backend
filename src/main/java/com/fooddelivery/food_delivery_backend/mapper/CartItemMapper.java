package com.fooddelivery.food_delivery_backend.mapper;

import com.fooddelivery.food_delivery_backend.dto.response.cart.CartItemResponse;
import com.fooddelivery.food_delivery_backend.entities.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartItemMapper {

    //ENTITY -> CART ITEM RESPONSE
    @Mapping(source = "menuItem.id",target = "menuItemId")
    @Mapping(source = "menuItem.name",target = "menuItemName")
    @Mapping(target = "subTotal", expression = "java(cartItem.getSubtotal())") // toget subtotal explicitly as mapper ignores @transient
    CartItemResponse toItemResponse(CartItem cartItem);
}
