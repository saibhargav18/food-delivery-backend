package com.fooddelivery.food_delivery_backend.dto.response.order;

import com.fooddelivery.food_delivery_backend.dto.response.address.AddressResponse;
import com.fooddelivery.food_delivery_backend.enums.OrderStatus;
import com.fooddelivery.food_delivery_backend.enums.PaymentMethod;
import com.fooddelivery.food_delivery_backend.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponse {

    private long id;
    private long customerId;
    private String customerName;
    private long restaurantId;
    private String restaurantName;
    private Long deliveryPartnerId;
    private AddressResponse deliveryAddress;
    private OrderStatus orderStatus;
    private PaymentMethod paymentMethod;
    private PaymentStatus paymentStatus;
    private List<OrderItemResponse> items;
    private BigDecimal itemsTotal;
    private BigDecimal deliveryCharges;
    private BigDecimal discount;
    private BigDecimal totalAmount;
    private LocalDateTime orderedAt;
    private LocalDateTime estimatedDeliveryTime;
    private LocalDateTime deliveredAt;
}
