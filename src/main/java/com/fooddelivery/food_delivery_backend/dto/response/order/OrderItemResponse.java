package com.fooddelivery.food_delivery_backend.dto.response.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItemResponse {

    private long id;
    private long menuItemId;
    private String menuItemName;
    private Integer quantity;
    private BigDecimal priceAtTimeOfOrder;
    private BigDecimal subTotal;


}
