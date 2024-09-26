package com.itsjaypatel.quickbites.notifications.templates.impl.email;


import com.itsjaypatel.quickbites.notifications.templates.EmailNotificationTemplate;
import com.itsjaypatel.quickbites.notifications.templates.constants.TemplateConstant;

import java.util.List;
import java.util.Map;

import static com.itsjaypatel.quickbites.notifications.templates.enums.NotificationTemplateCode.EMAIL_PICKUP_DELIVERY_CONFIRMATION_OTP_NOTIFICATION;


public class EmailPickupDeliveryConfirmationOTPNotification extends EmailNotificationTemplate {

    private static final List<String> fields = List
            .of(
                    TemplateConstant.RESTAURANT_PARTNER_NAME,
                    TemplateConstant.OTP
            );

    public EmailPickupDeliveryConfirmationOTPNotification(final Map<String,Object> payLoad) {
        super(payLoad, EMAIL_PICKUP_DELIVERY_CONFIRMATION_OTP_NOTIFICATION.getTemplate(),fields);
    }
}
