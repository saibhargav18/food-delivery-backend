package com.fooddelivery.food_delivery_backend.controller;

import com.fooddelivery.food_delivery_backend.dto.request.cart.AddToCartRequest;
import com.fooddelivery.food_delivery_backend.dto.request.cart.UpdateCartItemRequest;
import com.fooddelivery.food_delivery_backend.dto.response.cart.CartResponse;
import com.fooddelivery.food_delivery_backend.service.CartService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;


    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/items")
    public ResponseEntity<CartResponse> addToCart(@Valid @RequestBody AddToCartRequest request){
        CartResponse response = cartService.addCart(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/items")
    public ResponseEntity<CartResponse> updateCartItem(@Valid @RequestBody UpdateCartItemRequest request){
        CartResponse response = cartService.updateCart(request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/items/{cartItemId}")
    public ResponseEntity<CartResponse> removeItemFromCart(@PathVariable Long cartItemId){
        CartResponse response = cartService.removeItem(cartItemId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    public ResponseEntity<Void> clearCart(){
        cartService.clearCart();
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<CartResponse> getActiveCart(){

        CartResponse response = cartService.getActiveCart();
        return ResponseEntity.ok(response);

    }
}
