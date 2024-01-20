package com.example.plannerapi.domain.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskDto {
    private long id;
    private String title;
    private String description;
    private ZonedDateTime DueToStart;
    private ZonedDateTime DueToEnd;
    private enum status {
        NEW, OVERDUE, COMPLETED, DELETED
    }
    private int priority;
    private String tag;
    private UserDto user;
}
