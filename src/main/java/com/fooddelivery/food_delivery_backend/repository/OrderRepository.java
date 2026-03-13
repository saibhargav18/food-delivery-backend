package com.fooddelivery.food_delivery_backend.repository;

import com.fooddelivery.food_delivery_backend.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {

    // Find all orders for a customer
    List<Order> findByCustomerId(Long customerId);

    boolean existsByIdAndCustomerId(Long orderId,Long customerId);

    boolean findOrderDeliveryById(Long orderId);

    // Find all orders for a restaurant
    List<Order> findByRestaurantId(Long restaurantId);

    // Find all orders for a delivery partner
    List<Order> findByDeliveryPartnerId(Long deliveryPartnerId);

    //Find all orders to a delivery address
    List<Order> findByDeliveryAddressId(Long deliveryAddressId);
}
