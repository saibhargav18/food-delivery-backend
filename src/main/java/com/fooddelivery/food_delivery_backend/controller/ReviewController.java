package com.fooddelivery.food_delivery_backend.controller;

import com.fooddelivery.food_delivery_backend.dto.request.review.CreateReviewRequest;
import com.fooddelivery.food_delivery_backend.dto.response.review.ReviewResponse;
import com.fooddelivery.food_delivery_backend.service.ReviewService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;


    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public ResponseEntity<ReviewResponse> createReview(@Valid @RequestBody CreateReviewRequest request){
        ReviewResponse response = reviewService.createReview(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/my")
    public ResponseEntity<List<ReviewResponse>> getMyReviews(){
        List<ReviewResponse> responses = reviewService.getMyReviews();
        return ResponseEntity.ok(responses);
    }
}
