package com.itsjaypatel.quickbites.services.impl;

import com.itsjaypatel.quickbites.context.IContextHolder;
import com.itsjaypatel.quickbites.dtos.*;
import com.itsjaypatel.quickbites.entities.*;
import com.itsjaypatel.quickbites.exceptions.BadRequestException;
import com.itsjaypatel.quickbites.exceptions.ResourceConflictException;
import com.itsjaypatel.quickbites.exceptions.ResourceNotFoundException;
import com.itsjaypatel.quickbites.notifications.services.EmailService;
import com.itsjaypatel.quickbites.notifications.templates.EmailNotificationTemplate;
import com.itsjaypatel.quickbites.notifications.templates.NotificationTemplateManager;
import com.itsjaypatel.quickbites.notifications.templates.constants.TemplateConstant;
import com.itsjaypatel.quickbites.repositories.*;
import com.itsjaypatel.quickbites.services.DeliveryPartnerService;
import com.itsjaypatel.quickbites.services.OrderService;
import com.itsjaypatel.quickbites.services.WalletService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static com.itsjaypatel.quickbites.notifications.templates.enums.NotificationTemplateCode.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeliveryPartnerServiceImpl implements DeliveryPartnerService {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantPartnerRepository restaurantPartnerRepository;
    private final RestaurantRatingRepository restaurantRatingRepository;
    private final CustomerRatingRepository customerRatingRepository;
    private final CustomerRepository customerRepository;
    private final DeliveryPartnerRepository deliveryPartnerRepository;
    private final WalletService walletService;
    private final OrderService orderService;
    private final ModelMapper modelMapper;
    private final IContextHolder contextHolder;
    private final NotificationTemplateManager notificationTemplateManager;
    private final EmailService emailService;

    @Override
    public DeliveryPartnerDto getMyProfile() {
        return modelMapper.map(contextHolder.deliveryPartner(), DeliveryPartnerDto.class);
    }

    @Override
    public void setAvailability(Boolean isAvailable) {
        DeliveryPartner deliveryPartner = contextHolder.deliveryPartner();
        if (isAvailable == deliveryPartner.getIsAvailable()) {
            throw new BadRequestException("availability already set as " + isAvailable);
        }
        deliveryPartner.setIsAvailable(isAvailable);
        deliveryPartnerRepository.save(deliveryPartner);
    }

    @Override
    public List<OrderDto> viewOrders() {
        DeliveryPartner currentLoggedDeliveryPartner = this.contextHolder.deliveryPartner();
        List<OrderDto> orders = orderService
                .findByDeliveryPartner(currentLoggedDeliveryPartner)
                .stream()
                .map(o -> modelMapper.map(o, OrderDto.class))
                .toList();
        return orders;
    }

    @Override
    public void rateCustomer(Long customerId, Integer rating) {
        UserEntity me = contextHolder.user();
        Customer customer = this
                .customerRepository
                .findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Delivery partner not found with id " + customerId));
        UserEntity customerUser = customer.getUser();
        customerRatingRepository
                .save(new CustomerRating(me, customerUser, rating));

        Double updatedAverageRating = customerRatingRepository.averageRatingForCustomer(customerUser.getId());
        customer.setRating(updatedAverageRating);

        customerRepository.save(customer);
    }

    @Override
    public void rateRestaurant(Long restaurantId, Integer rating) {
        UserEntity user = contextHolder.user();
        Restaurant restaurant = restaurantRepository
                .findById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id: " + restaurantId));

        restaurantRatingRepository.save(new RestaurantRating(user, restaurant, rating));
        Double updatedAverageRating = restaurantRatingRepository.averageRatingForRestaurant(restaurant.getId());

        restaurant.setRating(updatedAverageRating);
        restaurantRepository.save(restaurant);
    }

    @Override
    public OrderDto acceptOrder(String orderId) {
        DeliveryPartner deliveryPartner = contextHolder.deliveryPartner();
        Wallet deliveryPartnerWallet = walletService.getWalletByUser(deliveryPartner.getUser());

        OrderEntity order = orderService.findById(orderId);
        if (order.getDeliveryPartner() != null) {
            throw new ResourceConflictException("Delivery partner already assigned to this order");
        }
        order.setDeliveryPartner(deliveryPartner);
        return modelMapper.map(orderService.save(order), OrderDto.class);
    }

    @Override
    public OrderDto pickUpOrder(OrderPickUpRequestDto request) {
        DeliveryPartner deliveryPartner = contextHolder.deliveryPartner();
        OrderEntity order = orderService.findById(request.getOrderId());
        if (!order.getDeliveryPartner().equals(deliveryPartner)) {
            throw new ResourceConflictException("Delivery partner with id " + deliveryPartner.getId() + " is not assigned to this order");
        }

        if(!order.getOrderStatus().equals(OrderStatus.BEING_PREPARED)){
            throw new ResourceConflictException("Not authorized to pick up order since it's marked as " + order.getOrderStatus());
        }

        if(!order.getRestaurantPartnerOtp().equals(request.getOtp())){
            throw new BadRequestException("Not authorized to pick up order since entered otp is not match for this order");
        }
        order.setOrderStatus(OrderStatus.ON_THE_WAY);
        OrderEntity updatedOrder = orderService.save(order);

        try{
            EmailDto emailDto = EmailDto
                    .builder()
                    .to(List.of(order.getCustomer().getUser().getEmail()))
                    .subject("Hurry!! Your Order is On The Way")
                    .build();
            final Map<String,Object> payLoad = Map
                    .of(
                            TemplateConstant.CUSTOMER_NAME, order.getCustomer().getUser().getFirstName(),
                            TemplateConstant.RESTAURANT_NAME,order.getRestaurant().getName(),
                            TemplateConstant.ORDER_ID,order.getId(),
                            TemplateConstant.DELIVER_PARTNER_NAME,deliveryPartner.getUser().getFirstName(),
                            TemplateConstant.TOTAL_ORDER_AMOUNT, order.getAmount());
            EmailNotificationTemplate notificationTemplate = notificationTemplateManager
                    .getEmailNotificationTemplate(payLoad,EMAIL_ORDER_ON_THE_WAY_NOTIFICATION);
            emailService.sendEmail(emailDto,notificationTemplate);
        }catch (Exception e){
            log.error("Error while sending notification for order on the way :: {}", e.getMessage());
        }

        try{
            EmailDto emailDto = EmailDto
                    .builder()
                    .to(List.of(order.getCustomer().getUser().getEmail()))
                    .subject("Order Delivery Confirmation OTP")
                    .build();
            final Map<String,Object> payLoad = Map
                    .of(TemplateConstant.CUSTOMER_NAME, order.getCustomer().getUser().getFirstName(),
                            TemplateConstant.OTP, order.getRestaurantPartnerOtp());
            EmailNotificationTemplate notificationTemplate = notificationTemplateManager
                    .getEmailNotificationTemplate(payLoad,EMAIL_DROP_DELIVERY_CONFIRMATION_OTP_NOTIFICATION);
            emailService.sendEmail(emailDto,notificationTemplate);
        }catch (Exception e){
            log.error("Error while sending notification for drop delivery confirmation otp :: {}", e.getMessage());
        }

        return modelMapper.map(updatedOrder, OrderDto.class);
    }

    @Override
    public OrderDto deliverOrder(OrderDeliveredRequestDto request) {
        DeliveryPartner deliveryPartner = contextHolder.deliveryPartner();
        OrderEntity order = orderService.findById(request.getOrderId());
        if (!order.getDeliveryPartner().equals(deliveryPartner)) {
            throw new ResourceConflictException("Delivery partner with id " + deliveryPartner.getId() + " is not assigned to this order");
        }

        if(!order.getOrderStatus().equals(OrderStatus.ON_THE_WAY)){
            throw new ResourceConflictException("Not authorized to pick up order since it's marked as " + order.getOrderStatus());
        }

        if(!order.getCustomerOtp().equals(request.getOtp())){
            throw new BadRequestException("Not authorized to pick up order since entered otp is not match for this order");
        }
        order.setOrderStatus(OrderStatus.DELIVERED);
        OrderEntity updatedOrder = orderService.save(order);

        //TODO: Implement Money Settlement

        RestaurantPartner restaurantPartner = restaurantPartnerRepository.findByRestaurantId(order.getRestaurant().getId()).get();

        Wallet restaurantPartnerWallet = walletService.getWalletByUser(restaurantPartner.getUser());
        Wallet deliveryPartnerWallet = walletService.getWalletByUser(deliveryPartner.getUser());
        List<Wallet> adminWalletList = walletService.findAdminWallet();

        Double restaurantPartnerShare = order.getAmount() * 0.7;
        Double deliveryPartnerShare = order.getAmount() * 0.15;
        walletService.credit(restaurantPartnerWallet, restaurantPartnerShare, String.valueOf(order.getId()));
        walletService.credit(deliveryPartnerWallet, deliveryPartnerShare, String.valueOf(order.getId()));
        adminWalletList.forEach(adminWallet -> walletService.deduct(adminWallet,  (restaurantPartnerShare + deliveryPartnerShare), String.valueOf(order.getId())));


        try{
            EmailDto emailDto = EmailDto
                    .builder()
                    .to(List.of(order.getCustomer().getUser().getEmail()))
                    .subject("Your Order has been Delivered")
                    .build();
            final Map<String,Object> payLoad = Map
                    .of(
                     TemplateConstant.CUSTOMER_NAME, order.getCustomer().getUser().getFirstName(),
                     TemplateConstant.RESTAURANT_NAME,order.getRestaurant().getName(),
                     TemplateConstant.ORDER_ID,order.getId(),
                     TemplateConstant.DELIVER_PARTNER_NAME,deliveryPartner.getUser().getFirstName(),
                     TemplateConstant.TOTAL_ORDER_AMOUNT, order.getAmount());
            EmailNotificationTemplate notificationTemplate = notificationTemplateManager
                    .getEmailNotificationTemplate(payLoad,EMAIL_ORDER_DELIVERED_NOTIFICATION);
            emailService.sendEmail(emailDto,notificationTemplate);
        }catch (Exception e){
            log.error("Error while sending notification for drop delivery confirmation otp :: {}", e.getMessage());
        }

        return modelMapper.map(updatedOrder, OrderDto.class);
    }

    @Override
    public OrderDto rejectOrder(String orderId) {
        DeliveryPartner deliveryPartner = contextHolder.deliveryPartner();
        OrderEntity order = orderService.findById(orderId);
        if (!order.getDeliveryPartner().equals(deliveryPartner)) {
            throw new ResourceConflictException("Delivery partner with id " + deliveryPartner.getId() + " is not assigned to this order");
        }
        if(!order.getOrderStatus().equals(OrderStatus.BEING_PREPARED)){
            throw new ResourceConflictException("Not authorized to reject order since it's marked as " + order.getOrderStatus());
        }

        //TODO: alert other delivery partner to accept this order
        List<DeliveryPartner> nearestDeliveryPartners =
                deliveryPartnerRepository
                .findNearestAroundRestaurant(order.getRestaurant().getAddress());

        order.setDeliveryPartner(null);
        OrderEntity updatedOrder = orderService.save(order);



        return modelMapper.map(updatedOrder, OrderDto.class);
    }
}
