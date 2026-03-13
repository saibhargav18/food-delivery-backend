package com.fooddelivery.food_delivery_backend.service;


import com.fooddelivery.food_delivery_backend.dto.request.restaurant.CreateRestaurantRequest;
import com.fooddelivery.food_delivery_backend.dto.request.restaurant.UpdateRestaurantRequest;
import com.fooddelivery.food_delivery_backend.dto.response.restaurant.RestaurantResponse;
import com.fooddelivery.food_delivery_backend.entities.Restaurant;
import com.fooddelivery.food_delivery_backend.entities.User;
import com.fooddelivery.food_delivery_backend.enums.UserRole;
import com.fooddelivery.food_delivery_backend.exception.ConflictException;
import com.fooddelivery.food_delivery_backend.exception.ForbiddenException;
import com.fooddelivery.food_delivery_backend.exception.NotFoundException;
import com.fooddelivery.food_delivery_backend.mapper.RestaurantMapper;
import com.fooddelivery.food_delivery_backend.repository.RestaurantRepository;
import com.fooddelivery.food_delivery_backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantMapper restaurantMapper;
    private final AuthService authService;
    private final UserRepository userRepository;


    public RestaurantService(RestaurantRepository restaurantRepository, RestaurantMapper restaurantMapper, AuthService authService, UserRepository userRepository) {
        this.restaurantRepository = restaurantRepository;
        this.restaurantMapper = restaurantMapper;
        this.authService = authService;
        this.userRepository = userRepository;
    }


    public RestaurantResponse createRestaurant(CreateRestaurantRequest request){

        User user = authService.getCurrentUser();

        if (restaurantRepository.existsByEmail(request.getEmail())){
            throw new ConflictException("Restaurant email already registered");
        }

        Restaurant restaurant = restaurantMapper.toEntity(request);

        user.setRole(UserRole.RESTAURANT_OWNER);
        userRepository.save(user);
        restaurant.setOwner(user);


        Restaurant saved =  restaurantRepository.save(restaurant);

        return restaurantMapper.toResponse(saved);

    }

    public RestaurantResponse updateRestaurant(Long id,UpdateRestaurantRequest request){

        Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(()->new NotFoundException("user not found"));
        User user = authService.getCurrentUser();

        if (!restaurant.getOwner().getId().equals(user.getId())){
            throw new ForbiddenException("You don't own this restaurant");
        }

        // Check if email is being changed and if new email already exists
        if (request.getEmail() != null &&
                !request.getEmail().equals(restaurant.getEmail()) &&
                restaurantRepository.existsByEmail(request.getEmail())) {
            throw new ConflictException("Email already in use by another restaurant");
        }

        restaurantMapper.updateRestaurantRequest(request, restaurant);
        Restaurant updated = restaurantRepository.save(restaurant);

        return restaurantMapper.toResponse(updated);

    }

    public RestaurantResponse getById(Long id){
        Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(()->new NotFoundException("user not found"));

        return restaurantMapper.toResponse(restaurant);
    }

    public List<RestaurantResponse> getRestaurantsByCity(String cityName){

        //return Collections.singletonList(restaurantMapper.toResponse((Restaurant) restaurantRepository.findByCityAndIsActiveTrue(cityName)));
        return restaurantRepository.findByCityAndIsActiveTrue(cityName)
                .stream()
                .map(restaurantMapper::toResponse)
                .toList();
    }

    public List<RestaurantResponse> getMyRestaurants(){
        User user = authService.getCurrentUser();
        //if (user.getRole()==UserRole.RESTAURANT_OWNER)

        return restaurantRepository.findByOwnerId(user.getId())
                .stream()
                .map(restaurantMapper::toResponse)
                .toList();
    }
}
