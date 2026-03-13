package com.fooddelivery.food_delivery_backend.service;

import com.fooddelivery.food_delivery_backend.dto.request.address.AddressRequest;
import com.fooddelivery.food_delivery_backend.dto.response.address.AddressResponse;
import com.fooddelivery.food_delivery_backend.entities.Address;
import com.fooddelivery.food_delivery_backend.entities.User;
import com.fooddelivery.food_delivery_backend.mapper.AddressMapper;
import com.fooddelivery.food_delivery_backend.repository.AddressRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {

    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;
    private final AuthService authService;

    public AddressService(AddressRepository addressRepository, AddressMapper addressMapper, AuthService authService) {
        this.addressRepository = addressRepository;
        this.addressMapper = addressMapper;
        this.authService = authService;
    }

    public AddressResponse createAddress(AddressRequest request){
        User user = authService.getCurrentUser();

        Address address = Address.builder()
                .user(user)
                .street(request.getStreet())
                .label(request.getLabel())
                .landmark(request.getLandmark())
                .city(request.getCity())
                .state(request.getState())
                .pincode(request.getPincode())
                .build();

        Address saved = addressRepository.save(address);

        return addressMapper.toResponse(saved);

    }

    public List<AddressResponse> getMyAddresses(){

        User user = authService.getCurrentUser();

        List<Address> addresses = addressRepository.findByUserId(user.getId());

        return addresses.stream()
                .map(addressMapper::toResponse)
                .toList();


    }
}
