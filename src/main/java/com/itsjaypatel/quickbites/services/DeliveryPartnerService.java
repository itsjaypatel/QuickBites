package com.itsjaypatel.quickbites.services;

import com.itsjaypatel.quickbites.dtos.DeliveryPartnerDto;
import com.itsjaypatel.quickbites.dtos.OrderDeliveredRequestDto;
import com.itsjaypatel.quickbites.dtos.OrderDto;
import com.itsjaypatel.quickbites.dtos.OrderPickUpRequestDto;

import java.util.List;

public interface DeliveryPartnerService {

    DeliveryPartnerDto getMyProfile();

    void setAvailability(Boolean isAvailable);

    List<OrderDto> viewOrders();

    void rateCustomer(Long customerId, Integer rating);

    void rateRestaurant(Long restaurantId, Integer rating);

    OrderDto acceptOrder(String orderId);

    OrderDto pickUpOrder(OrderPickUpRequestDto request);

    OrderDto deliverOrder(OrderDeliveredRequestDto request);

    OrderDto rejectOrder(String orderId);

}
