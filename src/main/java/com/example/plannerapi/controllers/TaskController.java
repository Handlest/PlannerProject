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
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;
    private final Mapper<TaskEntity, TaskDto> taskMapper;

    @PostMapping(path = "/tasks")
    public ResponseEntity<TaskDto> createTask(@RequestBody TaskCreateRequest taskRequest){
        TaskEntity savedTaskEntity = taskService.createTask(taskRequest);
        return new ResponseEntity<>(taskMapper.mapTo(savedTaskEntity), HttpStatus.CREATED);
    }

    @GetMapping(path = "/tasks")
    public ResponseEntity<List<TaskDto>> getAllTasks(){
        List<TaskEntity> savedTaskEntity = taskService.getAllTasks();
        return new ResponseEntity<>(savedTaskEntity.stream().map(taskMapper::mapTo).toList(), HttpStatus.OK);
    }

    @GetMapping(path = "/tasks/{id}")
    public ResponseEntity<TaskDto> getTaskById(@PathVariable Long id) {
        Optional<TaskEntity> savedTaskEntity = taskService.getTaskById(id);
        return savedTaskEntity.map(taskEntity -> new ResponseEntity<>(taskMapper.mapTo(taskEntity), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(path = "/tasks/{status}")
    public ResponseEntity<List<TaskDto>> getAllTasksByStatus(@PathVariable TaskEntity.Status status){
        List<TaskEntity> savedTaskEntity = taskService.getAllTasksWithStatus(status);
        return new ResponseEntity<>(savedTaskEntity.stream().map(taskMapper::mapTo).toList(), HttpStatus.OK);
    }

    @PutMapping(path = "/tasks")
    public ResponseEntity<TaskDto> updateTask(@RequestBody TaskDto taskDto) {
        Optional<TaskEntity> result = taskService.updateTask(taskDto);
        return result.map(taskEntity -> new ResponseEntity<>(taskMapper.mapTo(taskEntity), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping(path = "/tasks/{id}")
    public ResponseEntity<HttpStatus> deleteTask(@PathVariable long id) {
        taskService.deleteTaskById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
