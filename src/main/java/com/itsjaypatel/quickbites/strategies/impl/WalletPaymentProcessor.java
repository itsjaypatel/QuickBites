package com.itsjaypatel.quickbites.strategies.impl;

import com.itsjaypatel.quickbites.context.IContextHolder;
import com.itsjaypatel.quickbites.dtos.EmailDto;
import com.itsjaypatel.quickbites.dtos.PaymentDto;
import com.itsjaypatel.quickbites.entities.*;
import com.itsjaypatel.quickbites.enums.PaymentMethod;
import com.itsjaypatel.quickbites.enums.PaymentStatus;
import com.itsjaypatel.quickbites.exceptions.BadRequestException;
import com.itsjaypatel.quickbites.notifications.services.EmailService;
import com.itsjaypatel.quickbites.notifications.templates.EmailNotificationTemplate;
import com.itsjaypatel.quickbites.notifications.templates.NotificationTemplateManager;
import com.itsjaypatel.quickbites.notifications.templates.constants.TemplateConstant;
import com.itsjaypatel.quickbites.repositories.OrderRepository;
import com.itsjaypatel.quickbites.repositories.PaymentRepository;
import com.itsjaypatel.quickbites.services.WalletService;
import com.itsjaypatel.quickbites.strategies.PaymentProcessor;
import com.itsjaypatel.quickbites.utils.CommanUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static com.itsjaypatel.quickbites.notifications.templates.enums.NotificationTemplateCode.EMAIL_ORDER_CONFIRMED_NOTIFICATION;

@Slf4j
@Service
@RequiredArgsConstructor
public class WalletPaymentProcessor implements PaymentProcessor {

    private final WalletService walletService;
    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;
    private final ModelMapper modelMapper;
    private final IContextHolder contextHolder;
    private final NotificationTemplateManager notificationTemplateManager;
    private final EmailService emailService;

    @Override
    public PaymentDto process(String orderId) {

        Customer customer = contextHolder.customer();

        OrderEntity order = orderRepository
                .findById(orderId)
                .orElseThrow(
                        () -> new IllegalArgumentException("Order not found with id " + orderId));

        if (!customer.equals(order.getCustomer())) {
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
                                        .paymentMethod(PaymentMethod.WALLET)
                                        .build());
        if (payment.getPaymentStatus() == PaymentStatus.SUCCESS) {
            throw new BadRequestException("Payment is already paid for this order");
        }

        Wallet customerWallet = walletService
                .getWalletByUser(order.getCustomer().getUser());
        walletService.deduct(customerWallet, payment.getAmount(), String.valueOf(order.getId()));
        List<Wallet> adminWalletList = walletService.findAdminWallet();
        adminWalletList
                .forEach(adminWallet ->
                        walletService
                                .credit(adminWallet, payment.getAmount(), String.valueOf(order.getId()))
                );
        order.setOrderStatus(OrderStatus.CONFIRMED);
        order.setCustomerOtp(CommanUtil.generateOTP());
        order.setRestaurantPartnerOtp(CommanUtil.generateOTP());

        orderRepository.save(order);
        payment.setPaymentStatus(PaymentStatus.SUCCESS);
        payment.setOrder(order);

        Payment savedPayment = paymentRepository.save(payment);


        try{
            EmailDto emailDto = EmailDto
                    .builder()
                    .to(List.of(customer.getUser().getEmail()))
                    .subject("Bingo!! Your Order is Confirmed")
                    .build();
            final Map<String,Object> payLoad = Map
                    .of(
                            TemplateConstant.CUSTOMER_NAME, order.getCustomer().getUser().getFirstName(),
                            TemplateConstant.RESTAURANT_NAME,order.getRestaurant().getName(),
                            TemplateConstant.ORDER_ID,order.getId(),
                            TemplateConstant.TOTAL_ORDER_AMOUNT, order.getAmount());
            EmailNotificationTemplate notificationTemplate = notificationTemplateManager
                    .getEmailNotificationTemplate(payLoad,EMAIL_ORDER_CONFIRMED_NOTIFICATION);
            emailService.sendEmail(emailDto,notificationTemplate);
        }catch (Exception e){
            log.error("Error while sending notification for order confirmation :: {}", e.getMessage());
        }

        return modelMapper.map(savedPayment, PaymentDto.class);
    }
}
