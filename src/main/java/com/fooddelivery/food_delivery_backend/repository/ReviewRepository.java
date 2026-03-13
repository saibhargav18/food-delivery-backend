package com.fooddelivery.food_delivery_backend.repository;

import com.fooddelivery.food_delivery_backend.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review,Long> {

    // Find all reviews for a restaurant
    List<Review> findByRestaurantId(Long restaurantId);

    // Find all reviews by a user
    List<Review> findByUserId(Long userId);
    boolean existsByOrderId(Long orderId);

    // Find review by order (only one review per order)
    Optional<Review> findByOrderId(Long orderId);

    // Find latest reviews for a restaurant (for RestaurantDetailResponse)
    List<Review> findTop10ByRestaurantIdOrderByCreatedAtDesc(Long restaurantId);
}
