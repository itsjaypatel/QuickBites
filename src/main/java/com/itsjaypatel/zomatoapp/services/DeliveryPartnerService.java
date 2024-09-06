package com.itsjaypatel.zomatoapp.services;

import com.itsjaypatel.zomatoapp.dtos.DeliveryPartnerDto;
import com.itsjaypatel.zomatoapp.dtos.DeliveryPartnerOrderUpdateDto;
import com.itsjaypatel.zomatoapp.dtos.OrderDto;
import com.itsjaypatel.zomatoapp.entities.DeliveryPartner;
import com.itsjaypatel.zomatoapp.entities.OrderEntity;

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
