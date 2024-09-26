package com.itsjaypatel.quickbites.notifications.services;

import com.itsjaypatel.quickbites.dtos.EmailDto;
import com.itsjaypatel.quickbites.notifications.templates.EmailNotificationTemplate;

public interface EmailService {

    public void sendEmail(EmailDto emailDto, EmailNotificationTemplate notificationTemplate);
}
