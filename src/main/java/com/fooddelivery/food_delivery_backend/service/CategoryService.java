package com.fooddelivery.food_delivery_backend.service;

import com.fooddelivery.food_delivery_backend.dto.request.menu.CreateCategoryRequest;
import com.fooddelivery.food_delivery_backend.dto.response.menu.CategoryResponse;
import com.fooddelivery.food_delivery_backend.entities.Category;
import com.fooddelivery.food_delivery_backend.entities.Restaurant;
import com.fooddelivery.food_delivery_backend.entities.User;
import com.fooddelivery.food_delivery_backend.exception.ForbiddenException;
import com.fooddelivery.food_delivery_backend.exception.NotFoundException;
import com.fooddelivery.food_delivery_backend.mapper.CategoryMapper;
import com.fooddelivery.food_delivery_backend.repository.CategoryRepository;
import com.fooddelivery.food_delivery_backend.repository.RestaurantRepository;
import com.fooddelivery.food_delivery_backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final AuthService authService;
    private final RestaurantRepository restaurantRepository;


    public CategoryService(UserRepository userRepository, CategoryRepository categoryRepository, CategoryMapper categoryMapper, AuthService authService, RestaurantRepository restaurantRepository) {
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
        this.authService = authService;
        this.restaurantRepository = restaurantRepository;

    }

    public CategoryResponse createCategory(CreateCategoryRequest request){
        User user = authService.getCurrentUser();

        Restaurant restaurant = restaurantRepository.findById(request.getRestaurantId()).orElseThrow(()->new NotFoundException("Restaurant not found!"));


       // Long resId = request.getRestaurantId();

        if (!restaurant.getOwner().getId().equals(user.getId())){
            throw new ForbiddenException("You don't own this restaurant");
        }

        Category category =  categoryMapper.toEntity(request);
        category.setRestaurant(restaurant);
        Category saved = categoryRepository.save(category);
        return categoryMapper.toResponse(saved);
    }

    public List<CategoryResponse> getCategoriesByRestaurant(Long id){
        Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(()->new NotFoundException("Restaurant not found!"));

        return categoryRepository.findByRestaurantId(id)
                .stream()
                .map(categoryMapper::toResponse)
                .toList();
    }

    public void deleteCategory(Long id){

        Category category = categoryRepository.findById(id).orElseThrow(()->new NotFoundException("Category Not Found"));

        User user = authService.getCurrentUser();

        if (!category.getRestaurant().getOwner().getId().equals(user.getId())){
            throw new ForbiddenException("You don't own this restaurant");
        }

        categoryRepository.delete(category);


    }
}
