package com.itsjaypatel.quickbites.notifications.templates.impl.email;


import com.itsjaypatel.quickbites.notifications.templates.EmailNotificationTemplate;
import com.itsjaypatel.quickbites.notifications.templates.constants.TemplateConstant;

import java.util.List;
import java.util.Map;

import static com.itsjaypatel.quickbites.notifications.templates.enums.NotificationTemplateCode.EMAIL_ORDER_DELIVERED_NOTIFICATION;


public class EmailOrderDeliveredNotification extends EmailNotificationTemplate {

    private static final List<String> fields = List
            .of(TemplateConstant.CUSTOMER_NAME, TemplateConstant.ORDER_ID,TemplateConstant.DELIVER_PARTNER_NAME,TemplateConstant.TOTAL_ORDER_AMOUNT);

    public EmailOrderDeliveredNotification(final Map<String,Object> payLoad) {
        super(payLoad, EMAIL_ORDER_DELIVERED_NOTIFICATION.getTemplate(),fields);
    }

}
