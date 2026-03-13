package com.fooddelivery.food_delivery_backend.service;

import com.fooddelivery.food_delivery_backend.dto.request.menu.CreateMenuItemRequest;
import com.fooddelivery.food_delivery_backend.dto.request.menu.UpdateMenuItemRequest;
import com.fooddelivery.food_delivery_backend.dto.response.menu.MenuItemResponse;
import com.fooddelivery.food_delivery_backend.entities.Category;
import com.fooddelivery.food_delivery_backend.entities.MenuItem;
import com.fooddelivery.food_delivery_backend.entities.Restaurant;
import com.fooddelivery.food_delivery_backend.entities.User;
import com.fooddelivery.food_delivery_backend.exception.ForbiddenException;
import com.fooddelivery.food_delivery_backend.exception.NotFoundException;
import com.fooddelivery.food_delivery_backend.mapper.MenuItemMapper;
import com.fooddelivery.food_delivery_backend.repository.CategoryRepository;
import com.fooddelivery.food_delivery_backend.repository.MenuItemRepository;
import com.fooddelivery.food_delivery_backend.repository.RestaurantRepository;
import com.fooddelivery.food_delivery_backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuItemService {

    private final MenuItemRepository menuItemRepository;
    private final UserRepository userRepository;
    private final MenuItemMapper menuItemMapper;
    private final AuthService authService;
    private final RestaurantRepository restaurantRepository;
    private final CategoryRepository categoryRepository;


    public MenuItemService(MenuItemRepository menuItemRepository, UserRepository userRepository, MenuItemMapper menuItemMapper, AuthService authService, RestaurantRepository restaurantRepository, CategoryRepository categoryRepository) {
        this.menuItemRepository = menuItemRepository;
        this.userRepository = userRepository;
        this.menuItemMapper = menuItemMapper;
        this.authService = authService;
        this.restaurantRepository = restaurantRepository;
        this.categoryRepository = categoryRepository;
    }

    //creating menu item
    public MenuItemResponse createMenuItem(CreateMenuItemRequest request){
        User user = authService.getCurrentUser();

        Long resId =  request.getRestaurantId();

       Restaurant restaurant =  restaurantRepository.findById(resId).orElseThrow(()->new NotFoundException("Restaurant not found"));

       if (!restaurant.getOwner().getId().equals(user.getId())){
           throw new ForbiddenException("You don't own this restaurant");
       }

       MenuItem menuItem =  menuItemMapper.toEntity(request);

       menuItem.setRestaurant(restaurant);

        if (request.getCategoryId()!=null){
            Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow(()->new NotFoundException("Category Not found"));
            menuItem.setCategory(category);
        }

        MenuItem saved =   menuItemRepository.save(menuItem);
        return menuItemMapper.toResponse(saved);

    }

    //update
    public MenuItemResponse updateMenuItem(Long id, UpdateMenuItemRequest request){

        MenuItem menuItem = menuItemRepository.findById(id).orElseThrow(()->new NotFoundException("Menu item not found"));

        User user = authService.getCurrentUser();


        if (!menuItem.getRestaurant().getOwner().getId().equals(user.getId())){
            throw new ForbiddenException("You don't own this restaurant");
        }

       if (request.getCategoryId()!=null){
           Category category = categoryRepository.findById(request.getCategoryId())
                   .orElseThrow(() -> new NotFoundException("Category not found"));
           menuItem.setCategory(category);
       }

       menuItemMapper.updateMenuItem(request,menuItem);
        MenuItem update =  menuItemRepository.save(menuItem);
        return menuItemMapper.toResponse(update);

    }

    public MenuItemResponse getMenuItemById(Long id){
        MenuItem menuItem = menuItemRepository.findById(id).orElseThrow(()->new NotFoundException("Menu item not found"));
        return menuItemMapper.toResponse(menuItem);
    }

    public List<MenuItemResponse> getMenuItemByRestaurant(Long id){
        Restaurant restaurant =  restaurantRepository.findById(id).orElseThrow(()->new NotFoundException("Restaurant not found"));

        return menuItemRepository.findByRestaurantId(id)
                .stream()
                .map(menuItemMapper::toResponse)
                .toList();
    }

    public void deleteMenuItem(Long id){
        MenuItem menuItem = menuItemRepository.findById(id).orElseThrow(()->new NotFoundException("Menu item not found"));

        User user = authService.getCurrentUser();
        if (!menuItem.getRestaurant().getOwner().getId().equals(user.getId())){
            throw new ForbiddenException("You don't own this restaurant");
        }

        menuItemRepository.deleteById(id);


    }
}
