package com.fooddelivery.food_delivery_backend.mapper;

import com.fooddelivery.food_delivery_backend.dto.response.deliveryPartner.DeliveryPartnerResponse;
import com.fooddelivery.food_delivery_backend.entities.DeliveryPartner;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DeliveryPartnerMapper {


    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.name", target = "userName")
    @Mapping(source = "user.email", target = "userEmail")
    @Mapping(source = "user.phone", target = "userPhone")
    DeliveryPartnerResponse toResponse(DeliveryPartner deliveryPartner);
}
