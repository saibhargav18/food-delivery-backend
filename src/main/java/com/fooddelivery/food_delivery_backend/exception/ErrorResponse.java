package com.fooddelivery.food_delivery_backend.exception;

import java.time.LocalDateTime;

public record ErrorResponse(

        int status,
        String error,
        String message,
        String path,
        LocalDateTime timeStamp

) {}
