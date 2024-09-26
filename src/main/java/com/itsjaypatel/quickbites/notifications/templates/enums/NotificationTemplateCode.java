package com.itsjaypatel.quickbites.notifications.templates.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum NotificationTemplateCode {

    //onboard notification
    EMAIL_CUSTOMER_ONBOARD_NOTIFICATION("EMAIL_CUSTOMER_ONBOARD_NOTIFICATION"),
    EMAIL_DELIVERY_PARTNER_ONBOARD_NOTIFICATION("EMAIL_DELIVERY_PARTNER_ONBOARD_NOTIFICATION"),
    EMAIL_RESTAURANT_PARTNER_ONBOARD_NOTIFICATION("EMAIL_RESTAURANT_PARTNER_ONBOARD_NOTIFICATION"),

    //otp notification
    EMAIL_DROP_DELIVERY_CONFIRMATION_OTP_NOTIFICATION("EMAIL_DROP_DELIVERY_CONFIRMATION_OTP_NOTIFICATION"), // send to customer after order status changed to "out-of-delivery"
    EMAIL_PICKUP_DELIVERY_CONFIRMATION_OTP_NOTIFICATION("EMAIL_PICKUP_DELIVERY_CONFIRMATION_OTP_NOTIFICATION"), // send to restaurant partner after order status changed to "out-of-delivery"

    //order status notification - send to customer
    EMAIL_ORDER_CONFIRMED_NOTIFICATION("EMAIL_ORDER_CONFIRMED_NOTIFICATION"),
    EMAIL_ORDER_BEING_PREPARED_NOTIFICATION("EMAIL_ORDER_BEING_PREPARED_NOTIFICATION"),
    EMAIL_ORDER_ON_THE_WAY_NOTIFICATION("EMAIL_ORDER_ON_THE_WAY_NOTIFICATION"),
    EMAIL_ORDER_DELIVERED_NOTIFICATION("EMAIL_ORDER_DELIVERED_NOTIFICATION"),
    EMAIL_ORDER_CANCELLED_NOTIFICATION("EMAIL_ORDER_CANCELLED_NOTIFICATION");

    private final String code;
    private final String template;

    NotificationTemplateCode(String code){
        this.code = code;
        this.template = String.join("-",Arrays.stream(code.split("_")).map(String::toLowerCase).toArray(String[]::new));
    }
}
