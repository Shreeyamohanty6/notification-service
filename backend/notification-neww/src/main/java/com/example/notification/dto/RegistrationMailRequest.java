package com.example.notification.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class RegistrationMailRequest {

    @NotBlank
    private String name;

    @NotBlank
    @Email
    private String toEmail;

    public RegistrationMailRequest() {}

    public RegistrationMailRequest(String name, String toEmail) {
        this.name = name;
        this.toEmail = toEmail;
    }

    public String getName() { return name; }
    public String getToEmail() { return toEmail; }
    public void setName(String name) { this.name = name; }
    public void setToEmail(String toEmail) { this.toEmail = toEmail; }
}
