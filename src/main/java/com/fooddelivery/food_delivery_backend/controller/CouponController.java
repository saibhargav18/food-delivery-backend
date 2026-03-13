package com.fooddelivery.food_delivery_backend.controller;

import com.fooddelivery.food_delivery_backend.dto.request.coupon.CreateCouponRequest;
import com.fooddelivery.food_delivery_backend.dto.response.coupon.CouponResponse;
import com.fooddelivery.food_delivery_backend.service.CouponService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/coupons")
public class CouponController {

    private final CouponService couponService;


    public CouponController(CouponService couponService) {
        this.couponService = couponService;
    }

    @PostMapping
    public ResponseEntity<CouponResponse> createCoupon(@Valid @RequestBody CreateCouponRequest request){
        CouponResponse response = couponService.createCoupon(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<CouponResponse>> getActiveCoupons(){
        List<CouponResponse> responses = couponService.getActiveCoupons();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/validate")
    public ResponseEntity<CouponResponse> validateCoupon(@RequestParam String code, @RequestParam BigDecimal orderAmount){
        CouponResponse response = couponService.validateCoupon(code,orderAmount);
        return ResponseEntity.ok(response);
    }
}
