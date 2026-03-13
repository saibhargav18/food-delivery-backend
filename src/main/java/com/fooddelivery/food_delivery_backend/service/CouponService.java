package com.fooddelivery.food_delivery_backend.service;

import com.fooddelivery.food_delivery_backend.dto.request.coupon.CreateCouponRequest;
import com.fooddelivery.food_delivery_backend.dto.response.coupon.CouponResponse;
import com.fooddelivery.food_delivery_backend.entities.Coupon;
import com.fooddelivery.food_delivery_backend.exception.BadRequestException;
import com.fooddelivery.food_delivery_backend.exception.ConflictException;
import com.fooddelivery.food_delivery_backend.exception.NotFoundException;
import com.fooddelivery.food_delivery_backend.mapper.CouponMapper;
import com.fooddelivery.food_delivery_backend.repository.CouponRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class CouponService {

    private final CouponRepository couponRepository;
    private final CouponMapper couponMapper;


    public CouponService(CouponRepository couponRepository, CouponMapper couponMapper) {
        this.couponRepository = couponRepository;
        this.couponMapper = couponMapper;
    }

    public CouponResponse createCoupon(CreateCouponRequest request){
        if (couponRepository.existsByCode(request.getCode())) {
            throw new ConflictException("Coupon code already exists");
        }


        Coupon coupon =  couponMapper.toEntity(request);

        Coupon saved = couponRepository.save(coupon);
        return couponMapper.toResponse(saved);
    }

    public CouponResponse validateCoupon(String code, BigDecimal orderAmount){

        Coupon coupon = couponRepository.findByCodeAndIsActiveTrue(code).orElseThrow(()->new NotFoundException("Coupon Not Found"));

        LocalDate today = LocalDate.now();
        if (today.isBefore(coupon.getValidFrom()) || today.isAfter(coupon.getValidUntil()) ){
            throw new BadRequestException("Coupon Expired or not yet valid");
        }

        if (orderAmount.compareTo(coupon.getMinOrderValue())<0){
            throw new BadRequestException("Minimum order value not met");

        }

        return couponMapper.toResponse(coupon);

    }

    public List<CouponResponse> getActiveCoupons(){

        return couponRepository.findByIsActiveTrue()
                .stream()
                .map(couponMapper::toResponse)
                .toList();

    }
}
