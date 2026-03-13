package com.fooddelivery.food_delivery_backend.dto.request.order;

import com.fooddelivery.food_delivery_backend.entities.Order;
import com.fooddelivery.food_delivery_backend.enums.OrderStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateOrderStatusRequest {

    @NotNull(message = "Order ID is required")
    @Positive
    private Long orderId;

    @NotNull(message = "Order Status is required")
    private OrderStatus newStatus;
}
