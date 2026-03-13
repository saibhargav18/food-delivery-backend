package com.fooddelivery.food_delivery_backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "coupon_usage")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CouponUsage {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    private LocalDateTime usedAt;

    @PrePersist
    public void prePersist(){
        this.usedAt = LocalDateTime.now();
    }
}
