package com.fooddelivery.food_delivery_backend.dto.response.cart;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItemResponse {

    private long id;
    private long menuItemId;
    private String menuItemName;
    private Integer quantity;
    private BigDecimal priceAtTimeOfAdding;
    private BigDecimal subTotal;


}
