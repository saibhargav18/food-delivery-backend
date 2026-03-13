package com.fooddelivery.food_delivery_backend.dto.response.auth;

import com.fooddelivery.food_delivery_backend.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResponse {

    private long id;
    private String name;
    private String email;
    private String token;
    private UserRole role;


}
