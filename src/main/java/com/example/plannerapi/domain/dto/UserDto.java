package com.example.plannerapi.domain.dto;
import com.example.plannerapi.domain.entities.UserEntity;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private Long userId;
    private String username;
    private String password;
    private LocalDateTime registrationDateTime;
    private String email;
    private boolean isActive;
    private UserEntity.Role role;
}