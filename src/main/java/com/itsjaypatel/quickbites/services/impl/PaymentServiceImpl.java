package com.itsjaypatel.quickbites.services.impl;

import com.itsjaypatel.quickbites.dtos.OrderDto;
import com.itsjaypatel.quickbites.dtos.PaymentDto;
import com.itsjaypatel.quickbites.entities.CartItem;
import com.itsjaypatel.quickbites.entities.OrderEntity;
import com.itsjaypatel.quickbites.enums.PaymentMethod;
import com.itsjaypatel.quickbites.services.CartService;
import com.itsjaypatel.quickbites.services.OrderService;
import com.itsjaypatel.quickbites.services.PaymentService;
import com.itsjaypatel.quickbites.strategies.StrategyManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final CartService cartService;
    private final OrderService orderService;
    private final StrategyManager strategyManager;
    private final ModelMapper modelMapper;

    @Override
    public OrderDto checkout() {
        List<CartItem> cartItems = cartService
                .checkout();
        OrderEntity orderEntity = orderService
                .generateOrderFromCart(cartItems);
        return modelMapper.map(orderEntity, OrderDto.class);
    }

    @Override
    public PaymentDto makePayment(String orderId, PaymentMethod paymentMethod) {
        return strategyManager
                .getPaymentProcessor(paymentMethod)
                .process(orderId);
    }

    @Override
    public PaymentDto updatePayment(PaymentDto paymentDto) {
//        Payment payment = paymentRepository.findById(paymentDto.getId()).orElseThrow(
//                () -> new ResourceNotFoundException("Payment not found with id " + paymentDto.getId())
//        );
//        if(payment.getPaymentStatus() != PaymentStatus.PENDING) {
//            throw new ResourceConflictException("Payment status for payment with id" + paymentDto.getId() + " :: " + payment.getPaymentStatus());
//        }
//        if(paymentRepository.existsByTransactionId(paymentDto.getTransactionId())) {
//            throw new IllegalArgumentException("Duplicate transaction id " + paymentDto.getTransactionId());
//        }
//        OrderEntity order = payment.getOrder();
//        payment.setPaymentStatus(paymentDto.getPaymentStatus());
//        payment.setTransactionId(paymentDto.getTransactionId());
//
//        if(payment.getPaymentStatus() == PaymentStatus.SUCCESS) {
//            log.info("Payment received :: " + payment);
//            order.setOrderStatus(OrderStatus.CONFIRMED);
//            order.setCustomerOtp(CommanUtil.generateOTP());
//            order.setRestaurantPartnerOtp(CommanUtil.generateOTP());
//            /*
//            * TODO:
//            *  1. send notification to restaurant partner
//            *  2. if restaurant partner accept the order then perform step 4
//            *  3. if restaurant partner reject the order then ask reason for rejection
//            *  4. send notification to top 10 nearest delivery partner around restaurant location
//            *
//            * */
//
//        }else{
//            log.info("Payment failed :: " + payment);
//            order.setOrderStatus(OrderStatus.FAILED);
//        }
//        payment.setOrder(orderRepository.save(order));
//        payment = paymentRepository.save(payment);
//        return modelMapper.map(payment,PaymentDto.class);
        return null;
    }
}
