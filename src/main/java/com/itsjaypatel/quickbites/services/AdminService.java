package com.itsjaypatel.quickbites.services;

import com.itsjaypatel.quickbites.dtos.OnboardDeliveryPartnerRequest;
import com.itsjaypatel.quickbites.dtos.OnboardRestaurantPartnerRequest;

public interface AdminService {

    void onboardDeliveryPartner(OnboardDeliveryPartnerRequest request);

    void onboardRestaurantPartner(OnboardRestaurantPartnerRequest request);

    void terminateDeliveryPartner(Long userId);

    void terminateRestaurantPartner(Long userId);
}
