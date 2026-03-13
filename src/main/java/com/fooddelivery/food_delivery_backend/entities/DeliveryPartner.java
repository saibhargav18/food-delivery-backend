package com.fooddelivery.food_delivery_backend.entities;

import com.fooddelivery.food_delivery_backend.enums.VehicleType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "delivery_partners")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeliveryPartner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VehicleType vehicleType;

    @Column(nullable = false,unique = true)
    private String vehicleNumber;


    @Column(nullable = false)
    private Boolean isAvailable;

    @OneToOne
    @JoinColumn(name = "user_id",unique = true)
    private User user;


}
