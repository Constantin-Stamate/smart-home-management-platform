package com.smarthome.management.platform.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomRequest {

    @NotBlank(message = "Numele camerei este obligatoriu")
    @Size(max = 80, message = "Numele camerei este prea lung")
    private String name;

    @Size(max = 80, message = "Etajul este prea lung")
    private String floor;

    @Size(max = 140, message = "Descrierea este prea lunga")
    private String description;
}