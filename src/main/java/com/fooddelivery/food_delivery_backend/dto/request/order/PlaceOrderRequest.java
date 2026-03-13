package com.fooddelivery.food_delivery_backend.dto.request.order;

import com.fooddelivery.food_delivery_backend.entities.Coupon;
import com.fooddelivery.food_delivery_backend.enums.PaymentMethod;
import com.fooddelivery.food_delivery_backend.enums.PaymentStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlaceOrderRequest {

    @NotNull(message = "Delivery Address Id is required")
    @Positive()
    private Long deliveryAddressId;

    @NotNull(message = "Payment Method is required")
    private PaymentMethod paymentMethod;

    @Size(max = 20, message = "Coupon code must not exceed 20 characters")
    private String couponCode;
}
