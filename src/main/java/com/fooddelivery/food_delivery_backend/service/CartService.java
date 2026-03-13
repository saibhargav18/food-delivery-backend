package com.fooddelivery.food_delivery_backend.service;

import com.fooddelivery.food_delivery_backend.dto.request.cart.AddToCartRequest;
import com.fooddelivery.food_delivery_backend.dto.request.cart.UpdateCartItemRequest;
import com.fooddelivery.food_delivery_backend.dto.response.cart.CartItemResponse;
import com.fooddelivery.food_delivery_backend.dto.response.cart.CartResponse;
import com.fooddelivery.food_delivery_backend.entities.Cart;
import com.fooddelivery.food_delivery_backend.entities.CartItem;
import com.fooddelivery.food_delivery_backend.entities.MenuItem;
import com.fooddelivery.food_delivery_backend.entities.User;
import com.fooddelivery.food_delivery_backend.exception.BadRequestException;
import com.fooddelivery.food_delivery_backend.exception.ForbiddenException;
import com.fooddelivery.food_delivery_backend.exception.NotFoundException;
import com.fooddelivery.food_delivery_backend.mapper.CartItemMapper;
import com.fooddelivery.food_delivery_backend.mapper.CartMapper;
import com.fooddelivery.food_delivery_backend.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final CartMapper cartMapper;
    private final AuthService authService;
    private final MenuItemRepository menuItemRepository;
    private final CartItemRepository cartItemRepository;
    private final CartItemMapper cartItemMapper;


    public CartService(CartRepository cartRepository, CartMapper cartMapper, AuthService authService, MenuItemRepository menuItemRepository, CartItemRepository cartItemRepository, CartItemMapper cartItemMapper) {
        this.cartRepository = cartRepository;
        this.cartMapper = cartMapper;
        this.authService = authService;
        this.menuItemRepository = menuItemRepository;
        this.cartItemRepository = cartItemRepository;
        this.cartItemMapper = cartItemMapper;
    }

    private void recalculateCartTotal(Cart cart) {
        List<CartItem> items = cartItemRepository.findByCartId(cart.getId());

        BigDecimal total = items.stream()
                .map(item -> item.getPriceAtTimeOfAdding()
                        .multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        cart.setTotalAmount(total);
        cartRepository.save(cart);
    }

    @Transactional
    public CartResponse addCart(AddToCartRequest request){
        User user = authService.getCurrentUser();

        MenuItem menuItem = menuItemRepository.findById(request.getMenuItemId()).orElseThrow(()-> new NotFoundException("Menu item not found"));

        if (request.getQuantity() <= 0){
            throw new BadRequestException("Enter the valid quantity");
        }


        Cart cart = cartRepository.findByUserIdAndIsActiveTrue(user.getId()).orElseGet(()->{
            Cart newCart = new Cart();
            newCart.setUser(user);
            newCart.setRestaurant(menuItem.getRestaurant());
            newCart.setIsActive(true);
            return cartRepository.save(newCart);
        });

        if (!cart.getRestaurant().getId().equals(menuItem.getRestaurant().getId())){
            throw new BadRequestException("Cannot add items from different restaurant.Clear cart first");
        }

        Optional<CartItem> existingCartItem = cartItemRepository.findByCartIdAndMenuItemId(cart.getId(),request.getMenuItemId());

        if(existingCartItem.isPresent()){
            CartItem existing = existingCartItem.get();
            existing.setQuantity(request.getQuantity()+existing.getQuantity());
            cartItemRepository.save(existing);
            recalculateCartTotal(cart);

        }
        else {
            CartItem cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setQuantity(request.getQuantity());
            cartItem.setMenuItem(menuItem);
            cartItem.setPriceAtTimeOfAdding(menuItem.getPrice());
            cartItemRepository.save(cartItem);
            recalculateCartTotal(cart);

        }

        return buildCartResponse(cart);

    }

    @Transactional
    public CartResponse updateCart(UpdateCartItemRequest request){

        User user = authService.getCurrentUser();

        if (request.getQuantity()<=0){
            throw new BadRequestException("Enter the valid quantity");
        }

        Cart cart = cartRepository.findByUserIdAndIsActiveTrue(user.getId()).orElseThrow(()->new NotFoundException("Cart Not Found"));


        CartItem cartItem = cartItemRepository
                .findById(request.getCartItemId())
                .orElseThrow(() -> new NotFoundException("Cart item not found"));

        if (!cartItem.getCart().getUser().getId().equals(user.getId())){
            throw new ForbiddenException("Not your cart item");
        }

        cartItem.setQuantity(request.getQuantity());
        cartItemRepository.save(cartItem);
        recalculateCartTotal(cart);

        return buildCartResponse(cart);

    }

    @Transactional
    public CartResponse removeItem(Long cartItemId){
        User user = authService.getCurrentUser();

        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(()-> new NotFoundException("Item not in cart"));

        if (!cartItem.getCart().getUser().getId().equals(user.getId())) {
            throw new ForbiddenException("Not your cart item");
        }

        Cart cart = cartItem.getCart();

        cartItemRepository.delete(cartItem);

        recalculateCartTotal(cart);
        return buildCartResponse(cart);



    }

    @Transactional
    public void clearCart(){
        User user = authService.getCurrentUser();
        Cart cart = cartRepository.findByUserIdAndIsActiveTrue(user.getId()).orElseThrow(()->new NotFoundException("Cart Not Found"));
        cartItemRepository.deleteAllByCartId(cart.getId());
        cart.setIsActive(false);
        cartRepository.save(cart);
    }

    public CartResponse getActiveCart(){
        User user = authService.getCurrentUser();
        Cart cart = cartRepository.findByUserIdAndIsActiveTrue(user.getId()).orElseThrow(()->new NotFoundException("Cart Not Found"));

        return buildCartResponse(cart);

    }


    private CartResponse buildCartResponse(Cart cart) {
        CartResponse response = cartMapper.toResponse(cart);

        List<CartItem> items = cartItemRepository.findByCartId(cart.getId());
        List<CartItemResponse> itemResponses = items.stream()
                .map(cartItemMapper::toItemResponse)
                .toList();

        response.setItems(itemResponses);
        return response;
    }
}
