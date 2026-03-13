package com.fooddelivery.food_delivery_backend.repository;

import com.fooddelivery.food_delivery_backend.entities.DeliveryPartner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeliveryPartnerRepository extends JpaRepository<DeliveryPartner,Long> {

    // Find delivery partner by user ID
    Optional<DeliveryPartner> findByUserId(Long userId);


    // Find all available delivery partners
    List<DeliveryPartner> findByIsAvailableTrue();

}
