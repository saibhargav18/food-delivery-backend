package com.fooddelivery.food_delivery_backend.dto.response.coupon;

import com.fooddelivery.food_delivery_backend.enums.DiscountType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CouponResponse {


    private long id;
    private String code;
    private String description;
    private DiscountType discountType;
    private BigDecimal discountValue;
    private BigDecimal minOrderValue;
    private BigDecimal maxDiscountAmount;
    private LocalDate validFrom;
    private LocalDate validUntil;
    private int usageLimitPerUser;
    private int totalUsageLimit;
    private Boolean isActive;


}
