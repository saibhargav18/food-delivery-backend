package com.fooddelivery.food_delivery_backend.controller;

import com.fooddelivery.food_delivery_backend.dto.request.address.AddressRequest;
import com.fooddelivery.food_delivery_backend.dto.response.address.AddressResponse;
import com.fooddelivery.food_delivery_backend.service.AddressService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {

    private final AddressService addressService;


    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping
    public ResponseEntity<AddressResponse> createAddress(@Valid @RequestBody AddressRequest request){
        AddressResponse response = addressService.createAddress(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<AddressResponse>> getMyAddresses(){
        List<AddressResponse> responses = addressService.getMyAddresses();
        return ResponseEntity.ok(responses);

    }
}
