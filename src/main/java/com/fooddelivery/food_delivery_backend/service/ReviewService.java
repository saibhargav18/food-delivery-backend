package com.fooddelivery.food_delivery_backend.service;

import com.fooddelivery.food_delivery_backend.dto.request.review.CreateReviewRequest;
import com.fooddelivery.food_delivery_backend.dto.response.review.ReviewResponse;
import com.fooddelivery.food_delivery_backend.entities.Order;
import com.fooddelivery.food_delivery_backend.entities.Restaurant;
import com.fooddelivery.food_delivery_backend.entities.Review;
import com.fooddelivery.food_delivery_backend.entities.User;
import com.fooddelivery.food_delivery_backend.enums.OrderStatus;
import com.fooddelivery.food_delivery_backend.exception.BadRequestException;
import com.fooddelivery.food_delivery_backend.exception.ConflictException;
import com.fooddelivery.food_delivery_backend.exception.ForbiddenException;
import com.fooddelivery.food_delivery_backend.exception.NotFoundException;
import com.fooddelivery.food_delivery_backend.mapper.ReviewMapper;
import com.fooddelivery.food_delivery_backend.repository.OrderRepository;
import com.fooddelivery.food_delivery_backend.repository.RestaurantRepository;
import com.fooddelivery.food_delivery_backend.repository.ReviewRepository;
import com.fooddelivery.food_delivery_backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    private final AuthService authService;
    private final OrderRepository orderRepository;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final ReviewMapper reviewMapper;


    public ReviewService(AuthService authService, OrderRepository orderRepository, ReviewRepository reviewRepository, UserRepository userRepository, RestaurantRepository restaurantRepository, ReviewMapper reviewMapper) {
        this.authService = authService;
        this.orderRepository = orderRepository;
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
        this.reviewMapper = reviewMapper;
    }

    public ReviewResponse createReview(CreateReviewRequest request){
        User user = authService.getCurrentUser();

        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new NotFoundException("Order not found"));


        // Check if user owns this order
        if (!order.getCustomer().getId().equals(user.getId())) {
            throw new ForbiddenException("You don't have permission to review this order");
        }
        // Check if order is delivered
        if (order.getOrderStatus() != OrderStatus.DELIVERED) {
            throw new BadRequestException("Can only review delivered orders");
        }

        // Check if already reviewed
        if (reviewRepository.existsByOrderId(request.getOrderId())) {
            throw new ConflictException("Order already reviewed");
        }

        Review review = reviewMapper.toEntity(request);
        review.setUser(user);
        review.setRestaurant(order.getRestaurant());
        review.setOrder(order);

        Review saved = reviewRepository.save(review);
        // Update restaurant rating
        updateRestaurantRating(order.getRestaurant().getId());

        return reviewMapper.toResponse(saved);
    }

    // Helper method to update restaurant rating
    private void updateRestaurantRating(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new NotFoundException("Restaurant not found"));

        List<Review> reviews = reviewRepository.findByRestaurantId(restaurantId);

        restaurant.setTotalReviews(reviews.size());

        double avgRating = reviews.stream()
                .mapToInt(Review::getRating)
                .average()
                .orElse(0.0);
        restaurant.setAverageRating(avgRating);

        restaurantRepository.save(restaurant);
    }

    public List<ReviewResponse> getReviewsByRestaurant(Long restaurantId){
        return reviewRepository.findByRestaurantId(restaurantId)
                .stream()
                .map(reviewMapper::toResponse)
                .toList();
    }

    public List<ReviewResponse> getMyReviews(){
        User user = authService.getCurrentUser();
        return reviewRepository.findByUserId(user.getId())
                .stream()
                .map(reviewMapper::toResponse)
                .toList();
    }
}
