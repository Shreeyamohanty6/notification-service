package com.example.register.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class RegisterRequest {

    @NotBlank
    private String name;

    @NotBlank
    @Pattern(regexp = "^[0-9]{10}$", message = "phone must be 10 digits")
    private String phone;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;

    public RegisterRequest() {}

    public RegisterRequest(String name, String phone, String email, String password) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.password = password;
    }

    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }

    public void setName(String name) { this.name = name; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
}
