package com.smarthome.management.platform.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {

    @NotBlank(message = "Numele este obligatoriu")
    @Size(min = 3, max = 80, message = "Numele trebuie sa aiba intre 3 si 80 caractere")
    private String fullName;

    @NotBlank(message = "Emailul este obligatoriu")
    @Email(message = "Email invalid")
    private String email;

    @NotBlank(message = "Parola este obligatorie")
    @Size(min = 6, max = 80, message = "Parola trebuie sa aiba cel putin 6 caractere")
    private String password;

    private String confirmPassword;
}