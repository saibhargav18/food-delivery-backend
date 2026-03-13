package com.fooddelivery.food_delivery_backend.controller;

import com.fooddelivery.food_delivery_backend.dto.request.restaurant.CreateRestaurantRequest;
import com.fooddelivery.food_delivery_backend.dto.request.restaurant.UpdateRestaurantRequest;
import com.fooddelivery.food_delivery_backend.dto.response.menu.CategoryResponse;
import com.fooddelivery.food_delivery_backend.dto.response.menu.MenuItemResponse;
import com.fooddelivery.food_delivery_backend.dto.response.order.OrderResponse;
import com.fooddelivery.food_delivery_backend.dto.response.restaurant.RestaurantResponse;
import com.fooddelivery.food_delivery_backend.dto.response.review.ReviewResponse;
import com.fooddelivery.food_delivery_backend.service.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;
    private final MenuItemService menuItemService;
    private final CategoryService categoryService;
    private final ReviewService reviewService;
    private final OrderService orderService;


    public RestaurantController(RestaurantService restaurantService, MenuItemService menuItemService, CategoryService categoryService, ReviewService reviewService, OrderService orderService) {
        this.restaurantService = restaurantService;
        this.menuItemService = menuItemService;
        this.categoryService = categoryService;
        this.reviewService = reviewService;
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<RestaurantResponse> createRestaurant(@Valid @RequestBody CreateRestaurantRequest request){
        RestaurantResponse response = restaurantService.createRestaurant(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestaurantResponse> updateRestaurant(@PathVariable Long id,@Valid @RequestBody UpdateRestaurantRequest request){
        RestaurantResponse response = restaurantService.updateRestaurant(id,request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantResponse> getRestaurantById(@PathVariable Long id){
        RestaurantResponse response = restaurantService.getById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<RestaurantResponse>> getRestaurantsByCity(@RequestParam String cityName){
        List<RestaurantResponse> responses = restaurantService.getRestaurantsByCity(cityName);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/my")
    public ResponseEntity<List<RestaurantResponse>> getMyRestaurants(){
        List<RestaurantResponse> responses = restaurantService.getMyRestaurants();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{restaurantId}/menu")
    public ResponseEntity<List<MenuItemResponse>> getMenuItemsByRestaurant(@PathVariable Long restaurantId){
        List<MenuItemResponse> responses = menuItemService.getMenuItemByRestaurant(restaurantId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{restaurantId}/categories")
    public ResponseEntity<List<CategoryResponse>> getCategoriesByRestaurantId(@PathVariable Long restaurantId){
        List<CategoryResponse> responses = categoryService.getCategoriesByRestaurant(restaurantId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{restaurantId}/reviews")
    public ResponseEntity<List<ReviewResponse>> getReviewsByRestaurantId(@PathVariable Long restaurantId){
        List<ReviewResponse> responses = reviewService.getReviewsByRestaurant(restaurantId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{restaurantId}/orders")
    public ResponseEntity<List<OrderResponse>> getRestaurantOrders(@PathVariable Long restaurantId){
        List<OrderResponse> responses = orderService.getRestaurantOrders(restaurantId);
        return ResponseEntity.ok(responses);
    }
}
