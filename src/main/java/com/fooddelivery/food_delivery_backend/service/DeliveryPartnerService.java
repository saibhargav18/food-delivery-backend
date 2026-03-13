package com.fooddelivery.food_delivery_backend.service;

import com.fooddelivery.food_delivery_backend.dto.request.deliveryPartner.RegisterDeliveryPartnerRequest;
import com.fooddelivery.food_delivery_backend.dto.response.deliveryPartner.DeliveryPartnerResponse;
import com.fooddelivery.food_delivery_backend.entities.DeliveryPartner;
import com.fooddelivery.food_delivery_backend.entities.User;
import com.fooddelivery.food_delivery_backend.enums.UserRole;
import com.fooddelivery.food_delivery_backend.exception.BadRequestException;
import com.fooddelivery.food_delivery_backend.exception.ConflictException;
import com.fooddelivery.food_delivery_backend.exception.NotFoundException;
import com.fooddelivery.food_delivery_backend.mapper.DeliveryPartnerMapper;
import com.fooddelivery.food_delivery_backend.repository.DeliveryPartnerRepository;
import com.fooddelivery.food_delivery_backend.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DeliveryPartnerService {

    private final DeliveryPartnerRepository deliveryPartnerRepository;
    private final DeliveryPartnerMapper deliveryPartnerMapper;
    private final AuthService authService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public DeliveryPartnerService(DeliveryPartnerRepository deliveryPartnerRepository, DeliveryPartnerMapper deliveryPartnerMapper, AuthService authService, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.deliveryPartnerRepository = deliveryPartnerRepository;
        this.deliveryPartnerMapper = deliveryPartnerMapper;
        this.authService = authService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public DeliveryPartnerResponse registerDeliveryPartner(RegisterDeliveryPartnerRequest request){
        if (userRepository.existsByEmail(request.getEmail())){
            throw new ConflictException("Email Already registered");
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .phone(request.getPhone())
                .role(UserRole.DELIVERY_PARTNER)
                .isActive(true)
                .build();

        User savedUser = userRepository.save(user);

        DeliveryPartner deliveryPartner = DeliveryPartner.builder()
                .user(savedUser)
                .vehicleType(request.getVehicleType())
                .vehicleNumber(request.getVehicleNumber())
                .isAvailable(true)
                .build();
        DeliveryPartner saved = deliveryPartnerRepository.save(deliveryPartner);

        return deliveryPartnerMapper.toResponse(saved);
    }

    public DeliveryPartnerResponse getMyProfile(){
        User user = authService.getCurrentUser();

        if (user.getRole()!=UserRole.DELIVERY_PARTNER){
            throw new BadRequestException("Not a Delivery Partner");
        }

        DeliveryPartner deliveryPartner = deliveryPartnerRepository.findByUserId(user.getId()).orElseThrow(()-> new NotFoundException("Delivery partner profile not found"));

        return deliveryPartnerMapper.toResponse(deliveryPartner);
    }
}
