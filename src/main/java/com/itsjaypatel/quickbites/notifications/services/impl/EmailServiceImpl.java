package com.itsjaypatel.quickbites.notifications.services.impl;

import com.itsjaypatel.quickbites.dtos.EmailDto;
import com.itsjaypatel.quickbites.notifications.services.EmailService;
import com.itsjaypatel.quickbites.notifications.templates.EmailNotificationTemplate;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.nio.charset.StandardCharsets;

@Slf4j
@RequiredArgsConstructor
@Component
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    private final SpringTemplateEngine templateEngine;

    @Override
    public void sendEmail(EmailDto emailDto, EmailNotificationTemplate template) {

        try{
            String htmlContent = templateEngine
                    .process(
                            template.getTemplate(),
                            template.getContext()
                    );
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message,MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            helper.setTo(emailDto.getTo().toArray(new String[0]));
            helper.setCc(emailDto.getCc().toArray(new String[0]));
            helper.setCc(emailDto.getBcc().toArray(new String[0]));
            helper.setPriority(emailDto.getPriority().getCode());
            helper.setSubject(template.getTemplate());
            helper.setText(htmlContent, true);
//            for(Map.Entry<String, MultipartFile> file : email.getAttachments().entrySet()){
//                helper.addAttachment(file.getKey(),file.getValue());
//            }
            mailSender.send(message);
        }catch (MailException | MessagingException e) {
            log.error("Exception occurred while sending email :: ", e);
            throw new RuntimeException(e);
        }
    }
}
