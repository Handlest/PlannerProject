package com.example.plannerapi.domain.dto.requests;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "Запрос на создание новой задачи")
public class TaskCreateRequest {
    @Schema(description = "Заголовок задачи", example = "Купить молоко")
    @Size(max = 60, message = "Заголовок должен быть не длиннее 60 символов")
    @NotBlank(message = "Название задачи не может быть пустым")
    private String title;

    @Schema(description = "Описание задачи", example = "Бренд: домик в деревне, главное не перепутать!")
    private String description;

    @Schema(description = "Дата начала дедлайна")
    private LocalDateTime DueToStart;

    @Schema(description = "Дата окончания дедлайна")
    private LocalDateTime DueToEnd;

    @Schema(description = "Приоритет задачи", example = "1", defaultValue = "1")
    private Integer priority = 1;

    @Schema(description = "Тег для задачи", example = "Покупки")
    private String tag;
}