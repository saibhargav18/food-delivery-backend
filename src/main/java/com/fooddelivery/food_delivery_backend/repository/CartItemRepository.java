package com.fooddelivery.food_delivery_backend.repository;

import com.fooddelivery.food_delivery_backend.entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem,Long> {

    // Find all items in a cart
    List<CartItem> findByCartId(Long cartId);

    // Find specific item in cart
    Optional<CartItem> findByCartIdAndMenuItemId(Long cartId, Long menuItemId);

    void deleteAllByCartId(Long cartId);


}
