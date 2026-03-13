package com.fooddelivery.food_delivery_backend.service;

import com.fooddelivery.food_delivery_backend.dto.request.order.PlaceOrderRequest;
import com.fooddelivery.food_delivery_backend.dto.response.order.OrderItemResponse;
import com.fooddelivery.food_delivery_backend.dto.response.order.OrderResponse;
import com.fooddelivery.food_delivery_backend.entities.*;
import com.fooddelivery.food_delivery_backend.enums.*;
import com.fooddelivery.food_delivery_backend.exception.BadRequestException;
import com.fooddelivery.food_delivery_backend.exception.ForbiddenException;
import com.fooddelivery.food_delivery_backend.exception.NotFoundException;
import com.fooddelivery.food_delivery_backend.mapper.OrderItemMapper;
import com.fooddelivery.food_delivery_backend.mapper.OrderMapper;
import com.fooddelivery.food_delivery_backend.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final AuthService authService;
    private final OrderItemRepository orderItemRepository;
    private final OrderItemMapper orderItemMapper;
    private final RestaurantRepository restaurantRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final AddressRepository addressRepository;
    private final CouponRepository couponRepository;
    private final CouponUsageRepository couponUsageRepository;
    private final DeliveryPartnerRepository deliveryPartnerRepository;

    public OrderService(OrderRepository orderRepository, OrderMapper orderMapper, AuthService authService, OrderItemRepository orderItemRepository, OrderItemMapper orderItemMapper, RestaurantRepository restaurantRepository, CartRepository cartRepository, CartItemRepository cartItemRepository, AddressRepository addressRepository, CouponRepository couponRepository, CouponUsageRepository couponUsageRepository, DeliveryPartnerRepository deliveryPartnerRepository) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.authService = authService;
        this.orderItemRepository = orderItemRepository;
        this.orderItemMapper = orderItemMapper;
        this.restaurantRepository = restaurantRepository;
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.addressRepository = addressRepository;
        this.couponRepository = couponRepository;
        this.couponUsageRepository = couponUsageRepository;
        this.deliveryPartnerRepository = deliveryPartnerRepository;
    }


    public OrderResponse getOrderById(Long orderId){

        User user = authService.getCurrentUser();
       Order order =  orderRepository.findById(orderId).orElseThrow(()->new NotFoundException("Order Not Found"));


        boolean customer =  order.getCustomer().getId().equals(user.getId());
        boolean restaurantOwner =  order.getRestaurant().getOwner().getId().equals(user.getId());
        boolean deliveryPartner =  order.getDeliveryPartner()!=null && order.getDeliveryPartner().getUser().getId().equals(user.getId());

        if (!(customer || restaurantOwner || deliveryPartner)){
            throw new ForbiddenException("Don't have access");
        }

        return buildOrderResponse(order);
    }


    public List<OrderResponse> getMyOrders(){
        User user = authService.getCurrentUser();
        List<Order> orders = orderRepository.findByCustomerId(user.getId());

        return orders.stream()
                .map(this::buildOrderResponse)
                .toList();
    }

    //HELPER METHOD
    private OrderResponse buildOrderResponse(Order order){
        OrderResponse response = orderMapper.toResponse(order);

        List<OrderItem> items = orderItemRepository.findByOrderId(order.getId());

        List<OrderItemResponse> itemResponses = items.stream()
                .map(orderItemMapper::toItemResponse)
                .toList();

        response.setItems(itemResponses);

        return response;

    }

    public List<OrderResponse> getRestaurantOrders(Long restaurantId){
        User user = authService.getCurrentUser();
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(()->new NotFoundException("Restaurant Not Found!!"));

        if (!restaurant.getOwner().getId().equals(user.getId())){
            throw new ForbiddenException("You don't own this restaurant");
        }

        List<Order> orders = orderRepository.findByRestaurantId(restaurantId);

        return orders.stream()
                .map(this::buildOrderResponse)
                .toList();
    }

    @Transactional
    public OrderResponse updateOrderStatus(Long orderId, OrderStatus newStatus){
        User user = authService.getCurrentUser();
        Order order = orderRepository.findById(orderId)
                .orElseThrow(()->new NotFoundException("Order not found"));

        boolean isCustomer = order.getCustomer().getId().equals(user.getId());
        boolean isRestaurantOwner = order.getRestaurant().getOwner().getId().equals(user.getId());
        boolean isDeliveryPartner = order.getDeliveryPartner()!=null && order.getDeliveryPartner().getUser().getId().equals(user.getId());

        OrderStatus currentStatus = order.getOrderStatus();

        // Validate status transition
        if (!isValidStatusTransition(currentStatus, newStatus)) {
            throw new BadRequestException(
                    "Invalid status transition from " + currentStatus + " to " + newStatus
            );
        }

        if (newStatus == OrderStatus.CONFIRMED || newStatus == OrderStatus.PREPARING || newStatus == OrderStatus.READY){
            if (!isRestaurantOwner){
                throw new ForbiddenException("Only restaurant owner can update to this status");
            }
        }
        if (newStatus == OrderStatus.PICKED_UP || newStatus == OrderStatus.OUT_FOR_DELIVERY || newStatus == OrderStatus.DELIVERED){
            if (!isDeliveryPartner){
                throw new ForbiddenException("Only delivery partner can update to this status");
            }
        }

        if (newStatus == OrderStatus.CANCELLED){
            if (!isCustomer){
                throw new ForbiddenException("Only customer can cancel order");
            }
        }

        order.setOrderStatus(newStatus);

        if (newStatus == OrderStatus.CONFIRMED) {
            order.setConfirmedAt(LocalDateTime.now());
        }

        if (newStatus == OrderStatus.DELIVERED) {
            order.setDeliveredAt(LocalDateTime.now());

            if (order.getPaymentMethod() == PaymentMethod.CASH_ON_DELIVERY && order.getPaymentStatus() == PaymentStatus.PENDING) {
                order.setPaymentStatus(PaymentStatus.PAID);
            }
        }
        orderRepository.save(order);
        return buildOrderResponse(order);
    }

    private boolean isValidStatusTransition(OrderStatus current, OrderStatus next) {
        if (next == OrderStatus.CANCELLED) {
            return current == OrderStatus.PLACED || current == OrderStatus.CONFIRMED;
        }

        if (current == OrderStatus.PLACED && next == OrderStatus.CONFIRMED) return true;
        if (current == OrderStatus.CONFIRMED && next == OrderStatus.PREPARING) return true;
        if (current == OrderStatus.PREPARING && next == OrderStatus.READY) return true;
        if (current == OrderStatus.READY && next == OrderStatus.ASSIGNED) return true;
        if (current == OrderStatus.ASSIGNED && next == OrderStatus.PICKED_UP) return true;
        if (current == OrderStatus.PICKED_UP && next == OrderStatus.OUT_FOR_DELIVERY) return true;
        if (current == OrderStatus.OUT_FOR_DELIVERY && next == OrderStatus.DELIVERED) return true;

        return false;
    }

    @Transactional
    public OrderResponse placeOrder(PlaceOrderRequest request){
        User user = authService.getCurrentUser();

        //Getting the cart with cart items
        Cart cart = cartRepository.findByUserIdAndIsActiveTrue(user.getId())
                .orElseThrow(()->new NotFoundException("Cart not found"));

        List<CartItem> cartItems = cartItemRepository.findByCartId(cart.getId());

        if (cartItems.isEmpty()){
            throw new BadRequestException("Cart is Empty");
        }

        //validate address if it belongs to user
        Address address = addressRepository.findById(request.getDeliveryAddressId())
                .orElseThrow(()->new NotFoundException("Address not found"));

        if (!address.getUser().getId().equals(user.getId())){
            throw new ForbiddenException("Not your address");
        }

        //Create order entity
        Order order = Order.builder()
                .customer(user)
                .restaurant(cart.getRestaurant())
                .deliveryAddress(address)
                .paymentMethod(request.getPaymentMethod())
                .paymentStatus(
                        request.getPaymentMethod() == PaymentMethod.ONLINE
                                ? PaymentStatus.PAID      //  Online = paid immediately
                                : PaymentStatus.PENDING   //  COD = pending until delivery
                )
                .orderStatus(OrderStatus.PLACED)
                .orderedAt(LocalDateTime.now())
                .itemsTotal(BigDecimal.ZERO)
                .deliveryCharges(BigDecimal.ZERO)   //  Add default
                .discount(BigDecimal.ZERO)          //  Add default
                .totalAmount(BigDecimal.ZERO)       //  Add default to pass constraint
                .build();

        Order savedOrder = orderRepository.save(order);

        BigDecimal itemsTotal = BigDecimal.ZERO;

        for (CartItem cartItem : cartItems){
            OrderItem orderItem = OrderItem.builder()
                    .order(savedOrder)
                    .menuItem(cartItem.getMenuItem())
                    .quantity(cartItem.getQuantity())
                    .priceAtTimeOfOrder(cartItem.getPriceAtTimeOfAdding())
                    .build();
            orderItemRepository.save(orderItem);

            itemsTotal = itemsTotal.add(cartItem.getPriceAtTimeOfAdding().multiply(BigDecimal.valueOf(cartItem.getQuantity())));
        }

        //savedOrder.setItemsTotal(itemsTotal);

        BigDecimal discount = BigDecimal.ZERO;
        Coupon usedCoupon = null;

        if (request.getCouponCode()!=null && !request.getCouponCode().isBlank()){
            Coupon coupon = couponRepository.findByCode(request.getCouponCode())
                    .orElseThrow(()->new NotFoundException("Coupon Not Found"));

            // Validate coupon
            LocalDate today = LocalDate.now();
            if (today.isBefore(coupon.getValidFrom()) || today.isAfter(coupon.getValidUntil())) {
                throw new BadRequestException("Coupon expired or not yet valid");
            }

            //  Check usage limit per user
            int userUsageCount = couponUsageRepository.countByUserIdAndCouponId(user.getId(), coupon.getId());
            if (coupon.getUsageLimitPerUser() != null && userUsageCount >= coupon.getUsageLimitPerUser()) {
                throw new BadRequestException("You have already used this coupon maximum times");
            }

            // Check total usage limit
            int totalUsageCount = couponUsageRepository.countByCouponId(coupon.getId());
            if (coupon.getTotalUsageLimit() != null && totalUsageCount >= coupon.getTotalUsageLimit()) {
                throw new BadRequestException("Coupon usage limit reached");
            }

            if (itemsTotal.compareTo(coupon.getMinOrderValue()) < 0) {
                throw new BadRequestException("Minimum order value not met");
            }

            // Calculate discount
            if (coupon.getDiscountType() == DiscountType.FLAT) {
                discount = coupon.getDiscountValue();
            } else {
                discount = itemsTotal.multiply(coupon.getDiscountValue())
                        .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

                if (coupon.getMaxDiscountAmount() != null &&
                        discount.compareTo(coupon.getMaxDiscountAmount()) > 0) {
                    discount = coupon.getMaxDiscountAmount();
                }
            }

            usedCoupon = coupon;
            savedOrder.setCoupon(coupon);

        }

        BigDecimal deliveryCharges = BigDecimal.valueOf(50);

        BigDecimal totalAmount = itemsTotal.add(deliveryCharges).subtract(discount);

        savedOrder.setItemsTotal(itemsTotal);
        savedOrder.setDiscount(discount);
        savedOrder.setDeliveryCharges(deliveryCharges);
        savedOrder.setTotalAmount(totalAmount);

        cartItemRepository.deleteAllByCartId(cart.getId());
        cart.setIsActive(false);
        cartRepository.save(cart);

        Order finalOrder = orderRepository.save(savedOrder);

        if (usedCoupon!=null){
            CouponUsage couponUsage = CouponUsage.builder()
                    .coupon(usedCoupon)
                    .user(user)
                    .order(finalOrder)
                    .build();
            couponUsageRepository.save(couponUsage);

        }
        return buildOrderResponse(finalOrder);


    }

    public OrderResponse assignDeliveryPartner(Long orderId, Long deliveryPartnerId){
        User user = authService.getCurrentUser();

        Order order = orderRepository.findById(orderId)
                .orElseThrow(()->new NotFoundException("Order not found"));

        if (!order.getRestaurant().getOwner().getId().equals(user.getId())){
            throw new ForbiddenException("Only restaurant owner can assign delivery partner");
        }

        if (order.getOrderStatus()!=OrderStatus.READY){
            throw new BadRequestException("Can only assign delivery partner when order is READY");
        }

        DeliveryPartner deliveryPartner = deliveryPartnerRepository.findById(deliveryPartnerId)
                .orElseThrow(()-> new NotFoundException("Delivery Partner not Found"));

        if (!deliveryPartner.getIsAvailable()){
            throw new BadRequestException("Delivery partner not available");
        }

        order.setDeliveryPartner(deliveryPartner);
        order.setOrderStatus(OrderStatus.ASSIGNED);

        Order saved = orderRepository.save(order);

        return buildOrderResponse(saved);


    }
}
