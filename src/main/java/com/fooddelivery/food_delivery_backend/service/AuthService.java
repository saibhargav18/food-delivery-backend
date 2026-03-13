package com.fooddelivery.food_delivery_backend.service;

import com.fooddelivery.food_delivery_backend.dto.request.auth.LoginRequest;
import com.fooddelivery.food_delivery_backend.dto.request.auth.RegisterRequest;
import com.fooddelivery.food_delivery_backend.dto.response.auth.AuthResponse;
import com.fooddelivery.food_delivery_backend.dto.response.user.UserResponse;
import com.fooddelivery.food_delivery_backend.entities.User;
import com.fooddelivery.food_delivery_backend.enums.UserRole;
import com.fooddelivery.food_delivery_backend.exception.ConflictException;
import com.fooddelivery.food_delivery_backend.exception.NotFoundException;
import com.fooddelivery.food_delivery_backend.mapper.UserMapper;
import com.fooddelivery.food_delivery_backend.repository.UserRepository;
import com.fooddelivery.food_delivery_backend.util.JWTUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;


    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JWTUtil jwtUtil, AuthenticationManager authenticationManager, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.userMapper = userMapper;
    }

    public AuthResponse register(RegisterRequest request){

        if (userRepository.existsByEmail(request.getEmail())){
            throw new ConflictException("User Already Exists!" +request.getEmail());
        }

        // Create user
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .phone(request.getPhone())
                .role(UserRole.CUSTOMER)
                .isActive(true)
                .build();

        // Save user
        User savedUser = userRepository.save(user);

        // Generate token
        String token = jwtUtil.generateToken(savedUser.getEmail(), 1440);  // 24 hours

        // Build response
        return AuthResponse.builder()
                .id(savedUser.getId())
                .name(savedUser.getName())
                .email(savedUser.getEmail())
                .token(token)
                .role(savedUser.getRole())
                .build();

    }

    public AuthResponse login(LoginRequest request){

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user =userRepository.findByEmail(request.getEmail()).orElseThrow(()->new NotFoundException("User Not Found!" +request.getEmail()));
        String token = jwtUtil.generateToken(request.getEmail(),600);
        // Build response
        return AuthResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .token(token)
                .role(user.getRole())
                .build();



    }

    public User getCurrentUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found"));

    }
}
