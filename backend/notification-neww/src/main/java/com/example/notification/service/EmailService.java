package com.example.notification.service;

import com.example.notification.dto.RegistrationMailRequest;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private static final Logger log = LoggerFactory.getLogger(EmailService.class);
    private final JavaMailSender mailSender;

    @Value("${app.mail.from}")
    private String from;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendRegistrationMail(RegistrationMailRequest req) throws MessagingException {
        log.info("Sending registration email: to={}, from={}", req.getToEmail(), from);
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");
        helper.setTo(req.getToEmail());
        helper.setFrom(from);
        helper.setSubject("Congratulations! You have been registered");

        String html = "<div style=\"font-family:Arial, sans-serif\">" +
                "<h2>Hi " + escape(req.getName()) + ",</h2>" +
                "<p>Congratulations, your account has been successfully registered.</p>" +
                "<p>We’re excited to have you on board!</p>" +
                "<br/><p style=\"color:#777\">— Team</p></div>";

        helper.setText(html, true);
        mailSender.send(message);
        log.info("Email dispatched successfully to {}", req.getToEmail());
    }

    private String escape(String s) {
        if (s == null) return "";
        return s.replace("&","&amp;").replace("<","&lt;").replace(">","&gt;");
    }
}
