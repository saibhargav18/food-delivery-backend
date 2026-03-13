package com.fooddelivery.food_delivery_backend.controller;

import com.fooddelivery.food_delivery_backend.dto.request.order.PlaceOrderRequest;
import com.fooddelivery.food_delivery_backend.dto.request.order.UpdateOrderStatusRequest;
import com.fooddelivery.food_delivery_backend.dto.response.order.OrderResponse;
import com.fooddelivery.food_delivery_backend.enums.OrderStatus;
import com.fooddelivery.food_delivery_backend.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;


    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderResponse> placeOrder(@Valid @RequestBody PlaceOrderRequest request){
        OrderResponse response = orderService.placeOrder(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long orderId){
        OrderResponse response = orderService.getOrderById(orderId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/my")
    public ResponseEntity<List<OrderResponse>> getMyOrders(){
        List<OrderResponse> responses = orderService.getMyOrders();
        return ResponseEntity.ok(responses);
    }

    @PatchMapping("/{orderId}/status")
    public ResponseEntity<OrderResponse> updateOrderStatus(@PathVariable Long orderId, @RequestBody UpdateOrderStatusRequest request){

        OrderResponse response = orderService.updateOrderStatus(orderId,request.getNewStatus());
        return ResponseEntity.ok(response);

    }

    @PatchMapping("/{orderId}/assign-delivery-partner")
    public ResponseEntity<OrderResponse> assignDeliveryPartner(@PathVariable Long orderId,@RequestParam Long deliveryPartnerId){
        OrderResponse response = orderService.assignDeliveryPartner(orderId,deliveryPartnerId);
        return ResponseEntity.ok(response);
    }
}
