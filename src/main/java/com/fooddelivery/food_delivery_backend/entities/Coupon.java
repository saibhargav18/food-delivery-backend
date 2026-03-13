package com.fooddelivery.food_delivery_backend.entities;

import com.fooddelivery.food_delivery_backend.enums.DiscountType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "coupons")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
    private String code;


    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DiscountType discountType;

    @Column(nullable = false)
    private BigDecimal discountValue;

    @Column(nullable = false)
    @Builder.Default
    private BigDecimal minOrderValue = BigDecimal.ZERO;

    @Column(nullable = true)
    private BigDecimal maxDiscountAmount;

    @Column(nullable = false)
    private LocalDate validFrom;

    @Column(nullable = false)
    private LocalDate validUntil;

    @Column(nullable = false)
    private Integer usageLimitPerUser;

    @Column(nullable = false)
    private Integer totalUsageLimit;

    @Column(nullable = false)
    @Builder.Default
    private Boolean isActive = true;


}
