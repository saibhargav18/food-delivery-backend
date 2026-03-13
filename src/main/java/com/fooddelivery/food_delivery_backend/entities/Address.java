package com.fooddelivery.food_delivery_backend.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "addresses")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Address {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String street;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false,length = 10)
    private String pincode;

    @Column(nullable = true)
    private String landmark;

    @Column(nullable = false)
    private String label;

    @ManyToOne
    @JoinColumn(name = "user_Id")
    private User user;


}
