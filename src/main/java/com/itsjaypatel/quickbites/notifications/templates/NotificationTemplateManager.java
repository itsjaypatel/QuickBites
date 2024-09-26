package com.itsjaypatel.quickbites.notifications.templates;

import com.itsjaypatel.quickbites.notifications.templates.enums.NotificationTemplateCode;
import com.itsjaypatel.quickbites.notifications.templates.impl.email.*;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class NotificationTemplateManager {


    public EmailNotificationTemplate getEmailNotificationTemplate(
            final Map<String,Object> payLoad, NotificationTemplateCode templateCode) {

        return switch (templateCode) {
            case EMAIL_CUSTOMER_ONBOARD_NOTIFICATION ->
                    new EmailCustomerOnboardNotification(payLoad);
            case EMAIL_DELIVERY_PARTNER_ONBOARD_NOTIFICATION ->
                    new EmailDeliveryPartnerOnboardNotification(payLoad);
            case EMAIL_RESTAURANT_PARTNER_ONBOARD_NOTIFICATION ->
                    new EmailRestaurantPartnerOnboardNotification(payLoad);
            case EMAIL_DROP_DELIVERY_CONFIRMATION_OTP_NOTIFICATION ->
                    new EmailDropDeliveryConfirmationOTPNotification(payLoad);
            case EMAIL_PICKUP_DELIVERY_CONFIRMATION_OTP_NOTIFICATION ->
                    new EmailPickupDeliveryConfirmationOTPNotification(payLoad);
            case EMAIL_ORDER_CONFIRMED_NOTIFICATION ->
                    new EmailOrderConfirmedNotification(payLoad);
            case EMAIL_ORDER_BEING_PREPARED_NOTIFICATION ->
                    new EmailOrderBeingPreparedNotification(payLoad);
            case EMAIL_ORDER_ON_THE_WAY_NOTIFICATION ->
                    new EmailOrderOnTheWayNotification(payLoad);
            case EMAIL_ORDER_DELIVERED_NOTIFICATION ->
                    new EmailOrderDeliveredNotification(payLoad);
            case EMAIL_ORDER_CANCELLED_NOTIFICATION ->
                    new EmailOrderCancelledNotification(payLoad);
        };
    }
}
