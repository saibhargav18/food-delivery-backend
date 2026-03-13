package com.fooddelivery.food_delivery_backend.repository;

import com.fooddelivery.food_delivery_backend.entities.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant,Long> {

    // Find restaurants by city
    List<Restaurant> findByCity(String city);

    boolean existsByEmail(String email);

   // boolean existsByEmailAndName(String email,String name);

    // Find active restaurants in a city
    List<Restaurant> findByCityAndIsActiveTrue(String city);
    List<Restaurant> findByOwnerIdAndIsActiveTrue(Long ownerId);

    // Find restaurants by owner
    List<Restaurant> findByOwnerId(Long ownerId);




}
