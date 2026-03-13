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
public class UpdateRestaurantRequest {


    @Size(min = 2,max = 50,message = "Restaurant must be between 2 and 50 characters")
    private String name;

    private String description;


    @Pattern(regexp = "^[0-9]{10}$", message = "Contact number must be exactly 10 digits")
    private String contact;


    @Email(message = "Please enter a valid email address")
    private String email;


    @Size(min = 2,max = 75,message = "Street name must be between 2 and 75 characters")
    private String street;


    @Size(min = 2,max = 25,message = "City name must be between 2 and 25 characters")
    private String city;


    @Size(min = 2,max = 25,message = "State must be between 2 and 25 characters")
    private String state;


    @Pattern(regexp = "^[0-9]{6,10}$",message = "Pincode must be between 6 to 10 digits")
    private String pincode;


    private LocalTime openingTime;

    private LocalTime closingTime;
}
