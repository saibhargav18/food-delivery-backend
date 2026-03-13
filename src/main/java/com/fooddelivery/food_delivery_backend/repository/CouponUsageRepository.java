package com.fooddelivery.food_delivery_backend.repository;

import com.fooddelivery.food_delivery_backend.entities.CouponUsage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CouponUsageRepository extends JpaRepository<CouponUsage,Long> {

    // Count how many times a user used a coupon
    int countByUserIdAndCouponId(Long userId, Long couponId);

    // Find all usages of a coupon
    List<CouponUsage> findByCouponId(Long couponId);

    int countByCouponId(Long couponId);
}
