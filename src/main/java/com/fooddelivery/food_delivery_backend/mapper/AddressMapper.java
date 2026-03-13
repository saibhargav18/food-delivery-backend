package com.fooddelivery.food_delivery_backend.mapper;

import com.fooddelivery.food_delivery_backend.dto.response.address.AddressResponse;
import com.fooddelivery.food_delivery_backend.entities.Address;
import com.fooddelivery.food_delivery_backend.entities.Restaurant;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper {


    AddressResponse toResponse(Address address);
}
