package com.fooddelivery.food_delivery_backend.dto.response.cart;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartResponse {
    private long id;
    private long restaurantId;
    private String restaurantName;
    private List<CartItemResponse> items;
    private BigDecimal totalAmount;
}
