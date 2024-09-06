package com.itsjaypatel.zomatoapp.services.impl;

import com.itsjaypatel.zomatoapp.dtos.OrderDto;
import com.itsjaypatel.zomatoapp.dtos.PaymentDto;
import com.itsjaypatel.zomatoapp.dtos.PaymentSuccessRequestDto;
import com.itsjaypatel.zomatoapp.entities.*;
import com.itsjaypatel.zomatoapp.enums.PaymentMethod;
import com.itsjaypatel.zomatoapp.enums.PaymentStatus;
import com.itsjaypatel.zomatoapp.exceptions.BadRequestException;
import com.itsjaypatel.zomatoapp.exceptions.ResourceConflictException;
import com.itsjaypatel.zomatoapp.exceptions.ResourceNotFoundException;
import com.itsjaypatel.zomatoapp.repositories.CartRepository;
import com.itsjaypatel.zomatoapp.repositories.OrderRepository;
import com.itsjaypatel.zomatoapp.repositories.PaymentRepository;
import com.itsjaypatel.zomatoapp.services.CustomerService;
import com.itsjaypatel.zomatoapp.services.PaymentService;
import com.itsjaypatel.zomatoapp.services.WalletService;
import com.itsjaypatel.zomatoapp.utils.CommanUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final CustomerService customerService;
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;
    private final ModelMapper modelMapper;
    private final WalletService walletService;

    @Override
    public OrderDto checkout() {
        Customer customer = customerService.currentLoggedCustomer();
        Cart cart = cartRepository.findByCustomerId(customer.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Cart not associate with customer with id " + customer.getId()));
        if(cart.getCartItems().isEmpty()) {
            throw new BadRequestException("Cart is empty for customer with id " + customer.getId());
        }
        final Restaurant restaurant = cart.getCartItems()
                .stream()
                .findFirst()
                .map(CartItem::getFoodItem)
                .map(FoodItem::getRestaurant)
                .orElse(null);
        final OrderEntity order = orderRepository.save(OrderEntity.builder().id(System.currentTimeMillis()).customer(customer).orderStatus(OrderStatus.PENDING).restaurant(restaurant).build());
        Set<OrderItem> orderItems =
                cart
                .getCartItems()
                .stream()
                .map(cartItem ->
                        OrderItem
                            .builder()
                                .foodItem(cartItem.getFoodItem())
                                .quantity(cartItem.getQuantity())
                                .order(order)
                                .build()).collect(Collectors.toSet());


        Double cartAmount = cart
                .getCartItems()
                .stream().map(cartItem -> cartItem.getQuantity() * cartItem.getFoodItem().getPrice())
                .reduce(0.0, Double::sum);
        order.setOrderItems(orderItems);
        order.setAmount(cartAmount);

        return modelMapper.map(orderRepository.save(order), OrderDto.class);
    }

    @Override
    public PaymentDto makePayment(Long orderId, PaymentMethod paymentMethod) {

        Customer customer = customerService.currentLoggedCustomer();

        OrderEntity order = orderRepository
                .findById(orderId)
                .orElseThrow(
                    () -> new IllegalArgumentException("Order not found with id " + orderId));

        if(!customer.equals(order.getCustomer())) {
            throw new BadRequestException("Order is not associated with customer with id" + customer.getId());
        }

        Payment payment =
                paymentRepository
                        .findByOrderId(orderId)
                        .orElse(
                                Payment.
                                builder()
                                        .id("PYMT" + System.currentTimeMillis())
                                        .paymentStatus(PaymentStatus.PENDING)
                                        .amount(order.getAmount())
                                        .paymentMethod(paymentMethod)
                                        .build());
        if(payment.getPaymentStatus() == PaymentStatus.SUCCESS) {
            throw new BadRequestException("Payment is already paid for this order");
        }

        if(paymentMethod.equals(PaymentMethod.WALLET)){
            // deduct amount from customer wallet
            Wallet customerWallet = walletService
                    .getWalletByUser(order.getCustomer().getUser());
            walletService.deduct(customerWallet,payment.getAmount());
            List<Wallet> adminWalletList = walletService.findAdminWallet();
            adminWalletList
                    .forEach(adminWallet ->
                            walletService
                                    .credit(adminWallet,payment.getAmount())
                    );
        }else{
            throw new BadRequestException("Payment method is not supported :: " + paymentMethod.name());
        }
        return modelMapper.map(paymentRepository.save(payment),PaymentDto.class);
    }

    @Override
    public PaymentDto updatePayment(PaymentDto paymentDto){
        Payment payment = paymentRepository.findById(paymentDto.getId()).orElseThrow(
                () -> new ResourceNotFoundException("Payment not found with id " + paymentDto.getId())
        );
        if(payment.getPaymentStatus() != PaymentStatus.PENDING) {
            throw new ResourceConflictException("Payment status for payment with id" + paymentDto.getId() + " :: " + payment.getPaymentStatus());
        }
        if(paymentRepository.existsByTransactionId(paymentDto.getTransactionId())) {
            throw new IllegalArgumentException("Duplicate transaction id " + paymentDto.getTransactionId());
        }
        OrderEntity order = payment.getOrder();
        payment.setPaymentStatus(paymentDto.getPaymentStatus());
        payment.setTransactionId(paymentDto.getTransactionId());

        if(payment.getPaymentStatus() == PaymentStatus.SUCCESS) {
            log.info("Payment received :: " + payment);
            order.setOrderStatus(OrderStatus.CONFIRMED);
            order.setCustomerOtp(CommanUtil.generateOTP());
            order.setRestaurantPartnerOtp(CommanUtil.generateOTP());
            /*
            * TODO:
            *  1. send notification to restaurant partner
            *  2. if restaurant partner accept the order then perform step 4
            *  3. if restaurant partner reject the order then ask reason for rejection
            *  4. send notification to top 10 nearest delivery partner around restaurant location
            *
            * */

        }else{
            log.info("Payment failed :: " + payment);
            order.setOrderStatus(OrderStatus.FAILED);
        }
        payment.setOrder(orderRepository.save(order));
        payment = paymentRepository.save(payment);
        return modelMapper.map(payment,PaymentDto.class);
    }
}
