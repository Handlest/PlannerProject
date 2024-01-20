package com.example.plannerapi.domain.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private Long userId;
    private String login;
    private String password;
    private ZonedDateTime registrationDateTime;
    private String email;
    private boolean isActive;
    private CustomSettingsDto settings;
}
