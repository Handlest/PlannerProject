package com.example.plannerapi.controllers.api;

import com.example.plannerapi.domain.dto.TaskDto;
import com.example.plannerapi.domain.entities.TaskEntity;
import com.example.plannerapi.mappers.Mapper;
import com.example.plannerapi.services.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Description;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/tasks")
@Tag(name = "API для задач")
public class ApiTaskController {
    private final TaskService taskService;
    private final Mapper<TaskEntity, TaskDto> taskMapper;
}
