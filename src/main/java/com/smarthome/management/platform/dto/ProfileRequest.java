package com.smarthome.management.platform.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileRequest {

    @NotBlank(message = "Numele este obligatoriu")
    @Size(min = 3, max = 80, message = "Numele trebuie sa aiba intre 3 si 80 caractere")
    private String fullName;
}