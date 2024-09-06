package com.itsjaypatel.zomatoapp.services;

import com.itsjaypatel.zomatoapp.dtos.OnboardDeliveryPartnerRequest;
import com.itsjaypatel.zomatoapp.dtos.OnboardRestaurantPartnerRequest;

public interface AdminService {

    void onboardDeliveryPartner(OnboardDeliveryPartnerRequest request);

    void onboardRestaurantPartner(OnboardRestaurantPartnerRequest request);

    void terminateDeliveryPartner(Long userId);

    void terminateRestaurantPartner(Long userId);
}
