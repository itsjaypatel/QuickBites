package com.itsjaypatel.quickbites.notifications.templates.impl.email;


import com.itsjaypatel.quickbites.notifications.templates.EmailNotificationTemplate;

import java.util.List;
import java.util.Map;

import static com.itsjaypatel.quickbites.notifications.templates.enums.NotificationTemplateCode.EMAIL_ORDER_CANCELLED_NOTIFICATION;


public class EmailOrderCancelledNotification extends EmailNotificationTemplate {

    private static final List<String> fields = List
            .of();

    public EmailOrderCancelledNotification(final Map<String,Object> payLoad) {
        super(payLoad, EMAIL_ORDER_CANCELLED_NOTIFICATION.getTemplate(),fields);
    }
}
