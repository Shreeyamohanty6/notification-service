package com.example.notification.controller;

import com.example.notification.dto.RegistrationMailRequest;
import com.example.notification.service.EmailService;
import javax.mail.MessagingException;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private static final Logger log = LoggerFactory.getLogger(NotificationController.class);
    private final EmailService emailService;

    public NotificationController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/registration")
    public ResponseEntity<Map<String, String>> sendRegistration(@Valid @RequestBody RegistrationMailRequest req) {
        Map<String, String> body = new HashMap<String, String>();
        try {
            emailService.sendRegistrationMail(req);
            body.put("status", "EMAIL_SENT");
            return ResponseEntity.ok(body);
        } catch (MessagingException ex) {
            log.error("Failed to send email to {}: {}", req.getToEmail(), ex.getMessage());
            body.put("status", "EMAIL_FAILED");
            body.put("error", ex.getMessage());
            return ResponseEntity.status(502).body(body);
        }
    }
}
