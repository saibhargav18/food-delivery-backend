package com.fooddelivery.food_delivery_backend.controller;

import com.fooddelivery.food_delivery_backend.dto.request.auth.LoginRequest;
import com.fooddelivery.food_delivery_backend.dto.request.auth.RegisterRequest;
import com.fooddelivery.food_delivery_backend.dto.response.auth.AuthResponse;
import com.fooddelivery.food_delivery_backend.dto.response.user.UserResponse;
import com.fooddelivery.food_delivery_backend.entities.User;
import com.fooddelivery.food_delivery_backend.mapper.UserMapper;
import com.fooddelivery.food_delivery_backend.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final UserMapper userMapper;


    public AuthController(AuthService authService, UserMapper userMapper) {
        this.authService = authService;
        this.userMapper = userMapper;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> registerRequest(@Valid @RequestBody RegisterRequest request){
        AuthResponse response = authService.register(request);
         return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginRequest(@Valid @RequestBody LoginRequest request){
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser(){
        User user = authService.getCurrentUser();  // Get entity from service
        UserResponse response = userMapper.toResponse(user);  // Convert to DTO
        return ResponseEntity.ok(response);
    }
}
