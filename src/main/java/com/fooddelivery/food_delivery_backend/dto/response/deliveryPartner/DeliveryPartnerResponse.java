package com.fooddelivery.food_delivery_backend.dto.response.deliveryPartner;


import com.fooddelivery.food_delivery_backend.enums.VehicleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeliveryPartnerResponse {

    private Long id;
    private Long userId;
    private String userName;
    private String userEmail;
    private String userPhone;
    private VehicleType vehicleType;
    private String vehicleNumber;
    private Boolean isAvailable;


}
