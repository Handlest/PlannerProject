package com.example.plannerapi.domain.dto;
import com.example.plannerapi.domain.entities.TaskEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskDto {
    private long taskId;
    private long userId;
    private String title;
    private String description;
    private LocalDateTime DueToStart;
    private LocalDateTime DueToEnd;
    private TaskEntity.Status status;
    private int priority;
    private String tag;
}
