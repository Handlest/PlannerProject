package com.example.plannerapi.domain.dto.requests;

import com.example.plannerapi.domain.entities.UserSettingsEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Запрос на обновление данных пользователя")
public class UserUpdateRequest {
    @Schema(description = "Имя пользователя", example = "John")
    @Size(min = 2, max = 50, message = "Имя пользователя должно содержать от 2 до 50 символов")
    private String username;

    @Schema(description = "Почта", example = "some_mail@gmail.com")
    @Size(min = 5, max = 255, message = "Адрес электронной почты должен содержать от 5 до 255 символов")
    private String email;

    @Schema(description = "Пароль", example = "my_password123")
    @Size(min = 8, max = 255, message = "Длина пароля должна быть от 8 до 255 символов")
    private String password;

    @Schema(description = "Настройки для интерфейса пользователя")
    private UserSettingsEntity settings;
}
