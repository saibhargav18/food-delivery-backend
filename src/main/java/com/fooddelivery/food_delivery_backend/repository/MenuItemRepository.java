package com.fooddelivery.food_delivery_backend.repository;

import com.fooddelivery.food_delivery_backend.entities.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem,Long> {

    // Find all menu items for a restaurant
    List<MenuItem> findByRestaurantId(Long restaurantId);

    // Find available menu items for a restaurant
    List<MenuItem> findByRestaurantIdAndIsAvailableTrue(Long restaurantId);

    // Find menu items by category
    List<MenuItem> findByCategoryId(Long categoryId);


}
