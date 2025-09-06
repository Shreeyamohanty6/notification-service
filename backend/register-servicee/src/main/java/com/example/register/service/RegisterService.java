package com.example.register.service;

import com.example.register.dto.RegisterRequest;
import com.example.register.dto.RegisterResponse;
import com.example.register.dto.RegistrationMailRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class RegisterService {

    private static final Logger log = LoggerFactory.getLogger(RegisterService.class);
    private final RestTemplate restTemplate;

    @Value("${notification.url}")
    private String notificationUrl;

    public RegisterService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public RegisterResponse register(RegisterRequest req) {
        // Persist user in DB in real app. Here we just generate an ID.
        String userId = UUID.randomUUID().toString();

        // Auto-send email to the user's provided email
        RegistrationMailRequest mail = new RegistrationMailRequest(req.getName(), req.getEmail());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<RegistrationMailRequest> entity = new HttpEntity<>(mail, headers);

        try {
            ResponseEntity<Map> resp = restTemplate.postForEntity(notificationUrl, entity, Map.class);
            if (resp.getStatusCode().is2xxSuccessful()) {
                log.info("Notification acknowledged for user {} to {}", userId, req.getEmail());
                return new RegisterResponse(userId, "User registered and email sent.");
            } else {
                log.error("Notification failed with status {} for {}", resp.getStatusCode(), req.getEmail());
                return new RegisterResponse(userId, "User registered. Email failed to send.");
            }
        } catch (RestClientException ex) {
            log.error("Error calling notification service: {}", ex.getMessage());
            return new RegisterResponse(userId, "User registered. Email service unreachable.");
        }
    }
}
