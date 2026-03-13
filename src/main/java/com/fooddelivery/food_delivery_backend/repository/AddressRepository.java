package com.fooddelivery.food_delivery_backend.repository;

import com.fooddelivery.food_delivery_backend.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address,Long> {

    List<Address> findByUserId(Long userId);

}
