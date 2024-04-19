package com.example.plannerapi.controllers;

import com.example.plannerapi.domain.dto.TaskDto;
import com.example.plannerapi.domain.dto.requests.TaskCreateRequest;
import com.example.plannerapi.domain.entities.TaskEntity;
import com.example.plannerapi.mappers.Mapper;
import com.example.plannerapi.services.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;
    private final Mapper<TaskEntity, TaskDto> taskMapper;

    @PostMapping(path = "/tasks")
    public ResponseEntity<TaskDto> createTask(Principal principal, @RequestBody TaskCreateRequest taskRequest){
        TaskEntity savedTaskEntity = taskService.createTask(principal, taskRequest);
        return new ResponseEntity<>(taskMapper.mapTo(savedTaskEntity), HttpStatus.CREATED);
    }

    @GetMapping(path = "/tasks")
    public ResponseEntity<List<TaskDto>> getAllTasks(Principal principal){
        List<TaskEntity> savedTaskEntity = taskService.getAllTasks(principal);
        return new ResponseEntity<>(savedTaskEntity.stream().map(taskMapper::mapTo).toList(), HttpStatus.OK);
    }

    @GetMapping(path = "/tasks/{id}")
    public ResponseEntity<TaskDto> getTaskById(Principal principal, @PathVariable Long id) {
        Optional<TaskEntity> savedTaskEntity = taskService.getTaskById(principal, id);
        return savedTaskEntity.map(taskEntity -> new ResponseEntity<>(taskMapper.mapTo(taskEntity), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(path = "/tasks/{status}")
    public ResponseEntity<List<TaskDto>> getAllTasksByStatus(Principal principal, @PathVariable TaskEntity.Status status){
        List<TaskEntity> savedTaskEntity = taskService.getAllTasksWithStatus(principal, status);
        return new ResponseEntity<>(savedTaskEntity.stream().map(taskMapper::mapTo).toList(), HttpStatus.OK);
    }

    @PutMapping(path = "/tasks")
    public ResponseEntity<TaskDto> updateTask(Principal principal, @RequestBody TaskDto taskDto) {
        Optional<TaskEntity> result = taskService.updateTask(principal, taskDto);
        return result.map(taskEntity -> new ResponseEntity<>(taskMapper.mapTo(taskEntity), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping(path = "/tasks/{id}")
    public ResponseEntity<HttpStatus> deleteTask(Principal principal, @PathVariable long id) {
        taskService.deleteTaskById(principal, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
