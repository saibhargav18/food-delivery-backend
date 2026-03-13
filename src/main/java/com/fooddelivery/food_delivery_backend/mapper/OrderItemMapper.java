package com.fooddelivery.food_delivery_backend.mapper;

import com.fooddelivery.food_delivery_backend.dto.response.order.OrderItemResponse;
import com.fooddelivery.food_delivery_backend.entities.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {

    @Mapping(source = "menuItem.id",target = "menuItemId")
    @Mapping(source = "menuItem.name",target = "menuItemName")
    @Mapping(target = "subTotal", expression = "java(orderItem.getSubtotal())") // toget subtotal explicitly as mapper ignores @transient
    OrderItemResponse toItemResponse(OrderItem orderItem);
}
