package com.smarthome.management.platform.dto;

import com.smarthome.management.platform.model.enums.DeviceTier;
import com.smarthome.management.platform.model.enums.DeviceType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeviceRequest {

    @NotBlank(message = "Numele dispozitivului este obligatoriu")
    @Size(max = 100, message = "Numele dispozitivului este prea lung")
    private String name;

    @NotNull(message = "Tipul dispozitivului este obligatoriu")
    private DeviceType type;

    @NotNull(message = "Nivelul dispozitivului este obligatoriu")
    private DeviceTier tier = DeviceTier.STANDARD;

    @NotNull(message = "Camera este obligatorie")
    private Long roomId;

    @Size(max = 80, message = "Modul este prea lung")
    private String mode;

    @Size(max = 160, message = "Locatia este prea lunga")
    private String location;
}