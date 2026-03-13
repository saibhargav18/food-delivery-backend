package com.fooddelivery.food_delivery_backend.mapper;

import com.fooddelivery.food_delivery_backend.dto.request.coupon.CreateCouponRequest;
import com.fooddelivery.food_delivery_backend.dto.response.coupon.CouponResponse;
import com.fooddelivery.food_delivery_backend.entities.Coupon;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CouponMapper {



    CouponResponse toResponse(Coupon coupon);

    @Mapping(target = "id",ignore = true)
    Coupon toEntity(CreateCouponRequest request);
}
