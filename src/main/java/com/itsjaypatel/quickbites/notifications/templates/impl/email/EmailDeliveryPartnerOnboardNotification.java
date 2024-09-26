package com.itsjaypatel.quickbites.notifications.templates.impl.email;


import com.itsjaypatel.quickbites.notifications.templates.EmailNotificationTemplate;

import java.util.List;
import java.util.Map;

import static com.itsjaypatel.quickbites.notifications.templates.enums.NotificationTemplateCode.EMAIL_DELIVERY_PARTNER_ONBOARD_NOTIFICATION;


public class EmailDeliveryPartnerOnboardNotification extends EmailNotificationTemplate {

    private static final List<String> fields = List
            .of();

    public EmailDeliveryPartnerOnboardNotification(final Map<String,Object> payLoad) {
        super(payLoad, EMAIL_DELIVERY_PARTNER_ONBOARD_NOTIFICATION.getTemplate(),fields);
    }
}
