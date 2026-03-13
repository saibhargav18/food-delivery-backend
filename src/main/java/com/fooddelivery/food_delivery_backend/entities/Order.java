package com.fooddelivery.food_delivery_backend.entities;

import com.fooddelivery.food_delivery_backend.enums.OrderStatus;
import com.fooddelivery.food_delivery_backend.enums.PaymentMethod;
import com.fooddelivery.food_delivery_backend.enums.PaymentStatus;
import jakarta.persistence.*;
import jdk.jfr.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private User customer;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @ManyToOne
    @JoinColumn(name = "delivery_partner_id",nullable = true)
    private DeliveryPartner deliveryPartner;

    @ManyToOne
    @JoinColumn(name = "delivery_address_id")
    private Address deliveryAddress;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private OrderStatus orderStatus = OrderStatus.PLACED;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private PaymentMethod paymentMethod = PaymentMethod.ONLINE;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private PaymentStatus paymentStatus = PaymentStatus.PENDING;

    @Column(nullable = false)
    @Builder.Default
    private BigDecimal itemsTotal = BigDecimal.ZERO;

    @Column(nullable = false)
    @Builder.Default
    private BigDecimal deliveryCharges = BigDecimal.ZERO;

    @ManyToOne
    @JoinColumn(name = "coupon_id", nullable = true)
    private Coupon coupon;

    @Column(nullable = false)
    @Builder.Default
    private BigDecimal discount = BigDecimal.ZERO;

    @Column(nullable = false)
    private BigDecimal totalAmount;


    private LocalDateTime orderedAt;
    private LocalDateTime confirmedAt;
    private LocalDateTime deliveredAt;
    private LocalDateTime estimatedDeliveryTime;



    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems;


}
