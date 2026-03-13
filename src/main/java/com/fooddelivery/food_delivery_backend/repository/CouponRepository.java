package com.fooddelivery.food_delivery_backend.repository;

import com.fooddelivery.food_delivery_backend.entities.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CouponRepository extends JpaRepository<Coupon,Long> {

    // Find coupon by code
    Optional<Coupon> findByCode(String code);

    boolean existsByCode(String code);

    List<Coupon> findByIsActiveTrue();

    // Find coupon by code and active status
    Optional<Coupon> findByCodeAndIsActiveTrue(String code);
}
