package com.fooddelivery.food_delivery_backend.dto.response.user;

import com.fooddelivery.food_delivery_backend.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {

    private Long id;
    private String name;
    private String email;
    private String phone;
    private UserRole role;
    private LocalDateTime createdAt;
    private Boolean isActive;
}
