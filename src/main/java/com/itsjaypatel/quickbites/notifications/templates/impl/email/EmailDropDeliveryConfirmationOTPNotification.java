package com.itsjaypatel.quickbites.notifications.templates.impl.email;


import com.itsjaypatel.quickbites.notifications.templates.EmailNotificationTemplate;
import com.itsjaypatel.quickbites.notifications.templates.constants.TemplateConstant;

import java.util.List;
import java.util.Map;

import static com.itsjaypatel.quickbites.notifications.templates.enums.NotificationTemplateCode.EMAIL_DROP_DELIVERY_CONFIRMATION_OTP_NOTIFICATION;


public class EmailDropDeliveryConfirmationOTPNotification extends EmailNotificationTemplate {

    private static final List<String> fields = List
            .of(TemplateConstant.CUSTOMER_NAME,TemplateConstant.OTP);

    public EmailDropDeliveryConfirmationOTPNotification(final Map<String,Object> payLoad) {
        super(payLoad, EMAIL_DROP_DELIVERY_CONFIRMATION_OTP_NOTIFICATION.getTemplate(),fields);
    }
}
