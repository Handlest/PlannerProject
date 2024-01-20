package com.example.plannerapi.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomSettingsDto {
    private long settingId;
    private enum theme {
        dark, light
    }
    private enum mainColor {
        white, black, red, green, cia
    }
    private enum secondaryColor {
        white, black, red, green, cian
    }
    private boolean sendMessagesToEmail;
    private boolean sendNotifications;
    private UserDto user;
}
