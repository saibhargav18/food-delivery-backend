package com.fooddelivery.food_delivery_backend.mapper;


import com.fooddelivery.food_delivery_backend.dto.response.order.OrderResponse;
import com.fooddelivery.food_delivery_backend.entities.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring",uses = {AddressMapper.class})
public interface OrderMapper {


    @Mapping(source = "customer.id",target = "customerId")
    @Mapping(source = "customer.name",target = "customerName")
    @Mapping(source = "restaurant.id",target = "restaurantId")
    @Mapping(source = "restaurant.name",target = "restaurantName")
    @Mapping(source = "deliveryPartner.id",target = "deliveryPartnerId")
    @Mapping(source = "deliveryAddress",target = "deliveryAddress")
    @Mapping(target = "items",ignore = true)
    OrderResponse toResponse(Order order);
}
