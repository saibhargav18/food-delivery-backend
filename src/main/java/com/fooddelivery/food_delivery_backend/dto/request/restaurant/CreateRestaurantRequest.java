package com.fooddelivery.food_delivery_backend.dto.request.restaurant;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateRestaurantRequest {

    @NotBlank(message = "Restaurant name is required")
    @Size(min = 2,max = 50,message = "Restaurant must be between 2 and 50 characters")
    private String name;

    private String description;

    @NotBlank(message = "Contact number is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Contact number must be exactly 10 digits")
    private String contact;

    @NotBlank(message = "Email is required")
    @Email(message = "Please enter a valid email address")
    private String email;

    @NotBlank(message = "Street name is required")
    @Size(min = 2,max = 75,message = "Street name must be between 2 and 75 characters")
    private String street;

    @NotBlank(message = "City name is required")
    @Size(min = 2,max = 25,message = "City name must be between 2 and 25 characters")
    private String city;

    @NotBlank(message = "State is required")
    @Size(min = 2,max = 25,message = "State must be between 2 and 25 characters")
    private String state;

    @NotBlank(message = "Pincode is required")
    @Pattern(regexp = "^[0-9]{6,10}$",message = "Pincode must be between 6 to 10 digits")
    private String pincode;

    @NotNull(message = "Opening time is required")
    private LocalTime openingTime;

    @NotNull(message = "Closing time is required")
    private LocalTime closingTime;

}
