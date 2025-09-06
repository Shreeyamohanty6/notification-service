package com.example.register.dto;

public class RegistrationMailRequest {
    private String name;
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
