package com.fooddelivery.food_delivery_backend.dto.request.coupon;

import com.fooddelivery.food_delivery_backend.enums.DiscountType;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateCouponRequest {

    @NotBlank
    @Size(min = 3,max = 10)
    @Pattern(regexp = "^[A-Z0-9]+$")
    private String code;

    @Size(max=200)
    private String description;

    @NotNull(message = "Discount Type is required")
    private DiscountType discountType;

    @NotNull(message = "Discount Value is required")
    @DecimalMin("0.01")
    private BigDecimal discountValue;


    @NotNull(message = "Minimum Order Value is required")
    @DecimalMin("0")
    private BigDecimal minOrderValue;

    @DecimalMin("0")
    private BigDecimal maxDiscountAmount;

    @NotNull
    private LocalDate validFrom;

    @NotNull
    private LocalDate validUntil;

    @NotNull
    @Min(1)
    private Integer usageLimitPerUser;

    @NotNull
    @Min(1)
    private Integer totalUsageLimit;



}
