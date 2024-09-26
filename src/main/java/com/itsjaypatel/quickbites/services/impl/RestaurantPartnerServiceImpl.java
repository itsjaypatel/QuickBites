package com.itsjaypatel.quickbites.services.impl;

import com.itsjaypatel.quickbites.context.IContextHolder;
import com.itsjaypatel.quickbites.dtos.EmailDto;
import com.itsjaypatel.quickbites.dtos.FoodItemDto;
import com.itsjaypatel.quickbites.dtos.OrderDto;
import com.itsjaypatel.quickbites.dtos.RestaurantPartnerDto;
import com.itsjaypatel.quickbites.entities.*;
import com.itsjaypatel.quickbites.exceptions.BadRequestException;
import com.itsjaypatel.quickbites.exceptions.ResourceConflictException;
import com.itsjaypatel.quickbites.exceptions.ResourceNotFoundException;
import com.itsjaypatel.quickbites.notifications.services.EmailService;
import com.itsjaypatel.quickbites.notifications.templates.EmailNotificationTemplate;
import com.itsjaypatel.quickbites.notifications.templates.NotificationTemplateManager;
import com.itsjaypatel.quickbites.notifications.templates.constants.TemplateConstant;
import com.itsjaypatel.quickbites.repositories.DeliveryPartnerRepository;
import com.itsjaypatel.quickbites.services.FoodItemService;
import com.itsjaypatel.quickbites.services.OrderService;
import com.itsjaypatel.quickbites.services.RestaurantPartnerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.itsjaypatel.quickbites.notifications.templates.enums.NotificationTemplateCode.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestaurantPartnerServiceImpl implements RestaurantPartnerService {

    private final OrderService orderService;
    private final IContextHolder contextHolder;
    private final FoodItemService foodItemService;
    private final ModelMapper modelMapper;
    private final DeliveryPartnerRepository deliveryPartnerRepository;
    private final NotificationTemplateManager notificationTemplateManager;
    private final EmailService emailService;

    @Override
    public RestaurantPartnerDto getMyProfile() {
        return modelMapper.map(contextHolder.restaurantPartner(), RestaurantPartnerDto.class);
    }

    @Override
    public List<OrderDto> viewOrders() {
        RestaurantPartner restaurantPartner = contextHolder.restaurantPartner();
        List<OrderDto> orders = orderService
                .findByRestaurant(restaurantPartner.getRestaurant())
                .stream()
                .map(o -> modelMapper.map(o, OrderDto.class))
                .toList();
        return orders;
    }

    @Override
    public OrderDto acceptOrder(String orderId) {
        RestaurantPartner restaurantPartner = contextHolder.restaurantPartner();
        OrderEntity order = orderService.findById(orderId);
        Restaurant orderRestaurant = order.getOrderItems().stream()
                .filter(Objects::nonNull)
                .map(orderItem -> orderItem.getFoodItem().getRestaurant())
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found for order with id: " + orderId));
        Restaurant myRestaurant = restaurantPartner.getRestaurant();
        if (!myRestaurant.equals(orderRestaurant)) {
            throw new ResourceNotFoundException("Order with id " + orderId + " not associated with your restaurant");
        }

        if(!order.getOrderStatus().equals(OrderStatus.CONFIRMED)){
            throw new ResourceConflictException("Not authorized to accept order since it's marked as " + order.getOrderStatus());
        }

        order.setOrderStatus(OrderStatus.BEING_PREPARED);
        OrderEntity updatedOrder = orderService.save(order);

        //TODO: send notification to driver to accept order

//        try {
//            List<DeliveryPartner> nearestDeliveryPartners = deliveryPartnerRepository
//                    .findNearestAroundRestaurant(myRestaurant.getAddress());
//
//
//            for(DeliveryPartner partner : nearestDeliveryPartners){
//                EmailDto emailDto = EmailDto
//                        .builder()
//                        .to(List.of(partner.getUser().getEmail()))
//                        .subject("New Delivery Found!!").build();
//
//                Map<String,Object> notificationPayLoad = Map
//                        .of(TemplateConstant.FIRST_NAME,partner.getUser().getFirstName());
//                EmailNotificationTemplate template = notificationTemplateManager
//                        .getEmailNotificationTemplate(notificationPayLoad, null);
//                emailService.sendEmail(emailDto,template);
//            }
//
//        }catch (Exception exception){
//            log.error("Error while sending new delivery found notification", exception);
//        }

        try{
            EmailDto emailDto = EmailDto
                    .builder()
                    .to(List.of(restaurantPartner.getUser().getEmail()))
                    .subject("Order Pickup Confirmation OTP")
                    .build();
            final Map<String,Object> payLoad = Map
                    .of(TemplateConstant.RESTAURANT_PARTNER_NAME, restaurantPartner.getUser().getFirstName(),
                        TemplateConstant.OTP, order.getRestaurantPartnerOtp());
            EmailNotificationTemplate notificationTemplate = notificationTemplateManager
                    .getEmailNotificationTemplate(payLoad,EMAIL_PICKUP_DELIVERY_CONFIRMATION_OTP_NOTIFICATION);
            emailService.sendEmail(emailDto,notificationTemplate);
        }catch (Exception e){
            log.error("Error while sending notification for pickup confirmation otp :: {}", e.getMessage());
        }

        try{
            EmailDto emailDto = EmailDto
                    .builder()
                    .to(List.of(order.getCustomer().getUser().getEmail()))
                    .subject("Hurry!! Order Being Prepared")
                    .build();
            final Map<String,Object> payLoad = Map
                    .of(
                            TemplateConstant.CUSTOMER_NAME, order.getCustomer().getUser().getFirstName(),
                            TemplateConstant.RESTAURANT_NAME,order.getRestaurant().getName(),
                            TemplateConstant.ORDER_ID,order.getId(),
                            TemplateConstant.TOTAL_ORDER_AMOUNT, order.getAmount());
            EmailNotificationTemplate notificationTemplate = notificationTemplateManager
                    .getEmailNotificationTemplate(payLoad,EMAIL_ORDER_BEING_PREPARED_NOTIFICATION);
            emailService.sendEmail(emailDto,notificationTemplate);
        }catch (Exception e){
            log.error("Error while sending notification for order being prepared :: {}", e.getMessage());
        }

        return modelMapper.map(updatedOrder, OrderDto.class);
    }

    @Override
    public OrderDto rejectOrder(String orderId) {
        RestaurantPartner restaurantPartner = contextHolder.restaurantPartner();
        OrderEntity order = orderService.findById(orderId);
        Restaurant orderRestaurant = order.getOrderItems().stream()
                .filter(Objects::nonNull)
                .map(orderItem -> orderItem.getFoodItem().getRestaurant())
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found for order with id: " + order.getId()));
        Restaurant myRestaurant = restaurantPartner.getRestaurant();
        if (!myRestaurant.equals(orderRestaurant)) {
            throw new BadRequestException("Order with id " + orderId + " not associated with your restaurant");
        }
        if (!order.getOrderStatus().equals(OrderStatus.BEING_PREPARED)) {
            throw new ResourceConflictException("Not authorized to reject order since it's marked as " + order.getOrderStatus());
        }

        order.setOrderStatus(OrderStatus.CANCELLED);
        if(order.getDeliveryPartner() != null){
            //TODO: send alert to delivery partner to inform order has been rejected by restaurant partner
        }
        order.setDeliveryPartner(null);
        OrderEntity updatedOrder = orderService.save(order);

        try{
            EmailDto emailDto = EmailDto
                    .builder()
                    .to(List.of(restaurantPartner.getUser().getEmail()))
                    .subject("Order Cancellation Acknowledgement")
                    .build();
            final Map<String,Object> payLoad = Map
                    .of(TemplateConstant.CUSTOMER_NAME, order.getCustomer().getUser().getFirstName(),
                         TemplateConstant.RESTAURANT_NAME,myRestaurant.getName(),
                         TemplateConstant.ORDER_ID, order.getId(),
                            TemplateConstant.CANCELLATION_REASON, "Due To High Demand",
                            TemplateConstant.TOTAL_ORDER_AMOUNT,order.getAmount());
            EmailNotificationTemplate notificationTemplate = notificationTemplateManager
                    .getEmailNotificationTemplate(payLoad,EMAIL_ORDER_CANCELLED_NOTIFICATION);
            emailService.sendEmail(emailDto,notificationTemplate);
        }catch (Exception e){
            log.error("Error while sending notification for order cancellation :: {}", e.getMessage());
        }

        return modelMapper.map(updatedOrder, OrderDto.class);
    }

    @Override
    public FoodItemDto addFoodItem(FoodItemDto requestDto) {
        RestaurantPartner restaurantPartner = contextHolder.restaurantPartner();
        FoodItem foodItem = modelMapper.map(requestDto, FoodItem.class);
        foodItem.setId(null);
        foodItem.setRating(0.0);
        foodItem.setRestaurant(restaurantPartner.getRestaurant());
        return modelMapper.map(foodItemService.save(foodItem), FoodItemDto.class);
    }

    @Override
    public FoodItemDto removeFoodItem(Long id) {
        RestaurantPartner current = contextHolder.restaurantPartner();
        FoodItem foodItem = foodItemService.findById(id);
        if (!foodItem.getRestaurant().equals(current.getRestaurant())) {
            throw new BadRequestException("Food item is not associated with your restaurant");
        }
        foodItemService.delete(foodItem.getId());
        return modelMapper.map(foodItem, FoodItemDto.class);
    }
}
