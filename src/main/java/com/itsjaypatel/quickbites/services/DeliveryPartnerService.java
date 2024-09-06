package com.itsjaypatel.quickbites.services;

import com.itsjaypatel.quickbites.dtos.DeliveryPartnerDto;
import com.itsjaypatel.quickbites.dtos.DeliveryPartnerOrderUpdateDto;
import com.itsjaypatel.quickbites.dtos.OrderDto;

import java.util.List;

public interface DeliveryPartnerService {

    DeliveryPartnerDto getMyProfile();

    List<OrderDto> viewOrders();

    void rateCustomer(Long customerId, Integer rating);

    void rateRestaurant(Long restaurantId, Integer rating);

    OrderDto acceptOrder(OrderDto orderDto);

//    OrderDto rejectOrder(OrderDto orderDto);

    OrderDto updateOrderStatus(DeliveryPartnerOrderUpdateDto updateDto);

}
