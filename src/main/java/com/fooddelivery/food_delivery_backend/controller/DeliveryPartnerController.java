package com.fooddelivery.food_delivery_backend.controller;

import com.fooddelivery.food_delivery_backend.dto.request.deliveryPartner.RegisterDeliveryPartnerRequest;
import com.fooddelivery.food_delivery_backend.dto.response.deliveryPartner.DeliveryPartnerResponse;
import com.fooddelivery.food_delivery_backend.service.DeliveryPartnerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/delivery-partners")
public class DeliveryPartnerController {

    private final DeliveryPartnerService deliveryPartnerService;


    public DeliveryPartnerController(DeliveryPartnerService deliveryPartnerService) {
        this.deliveryPartnerService = deliveryPartnerService;
    }

    @PostMapping("/register")
    public ResponseEntity<DeliveryPartnerResponse> registerDeliveryPartner(@Valid @RequestBody RegisterDeliveryPartnerRequest request){
        DeliveryPartnerResponse response = deliveryPartnerService.registerDeliveryPartner(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/me")
    public ResponseEntity<DeliveryPartnerResponse> getMyProfile(){
        return ResponseEntity.ok(deliveryPartnerService.getMyProfile());
    }
}
