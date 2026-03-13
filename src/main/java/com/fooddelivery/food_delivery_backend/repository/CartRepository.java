package com.fooddelivery.food_delivery_backend.repository;

import com.fooddelivery.food_delivery_backend.entities.Cart;
import com.fooddelivery.food_delivery_backend.entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {

    // Find active cart for a user
    Optional<Cart> findByUserIdAndIsActiveTrue(Long userId);



}
