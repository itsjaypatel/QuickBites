package com.itsjaypatel.quickbites.notifications.templates.impl.email;


import com.itsjaypatel.quickbites.notifications.templates.EmailNotificationTemplate;
import com.itsjaypatel.quickbites.notifications.templates.constants.TemplateConstant;

import java.util.List;
import java.util.Map;

import static com.itsjaypatel.quickbites.notifications.templates.enums.NotificationTemplateCode.EMAIL_CUSTOMER_ONBOARD_NOTIFICATION;


public class EmailCustomerOnboardNotification extends EmailNotificationTemplate {

    private static final List<String> fields = List
            .of(TemplateConstant.FIRST_NAME);

    public EmailCustomerOnboardNotification(final Map<String,Object> payLoad) {
        super(payLoad, EMAIL_CUSTOMER_ONBOARD_NOTIFICATION.getTemplate(),fields);
    }
}
