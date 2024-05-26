package com.example.plannerapi.controllers;

import com.example.plannerapi.domain.dto.TaskDto;
import com.example.plannerapi.domain.dto.requests.TaskCreateRequest;
import com.example.plannerapi.domain.entities.TaskEntity;
import com.example.plannerapi.mappers.Mapper;
import com.example.plannerapi.controllers.specifications.TaskSpecifications;
import com.example.plannerapi.services.TaskService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import static com.example.plannerapi.controllers.specifications.TaskSpecifications.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "Работа с задачами")
public class TaskController {
    private final TaskService taskService;
    private final Mapper<TaskEntity, TaskDto> taskMapper;

    @PostMapping(path = "/tasks")
    public ResponseEntity<TaskDto> createTask(Principal principal, @RequestBody TaskCreateRequest taskRequest){
        TaskEntity savedTaskEntity = taskService.createTask(principal, taskRequest);
        return new ResponseEntity<>(taskMapper.mapTo(savedTaskEntity), HttpStatus.CREATED);
    }

    @GetMapping(path = "/tasks")
    public ResponseEntity<List<TaskDto>> getAllTasks(
            Principal principal,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size,
            @RequestParam(defaultValue = "taskId, asc") String[] sort,
            @RequestParam(required = false) TaskEntity.Status status,
            @RequestParam(required = false) Integer priority,
            @RequestParam(required = false) String tag) {
        String sortBy = sort[0];
        Sort.Direction direction = Sort.Direction.fromString(sort[1]);
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        Specification<TaskEntity> specification = Specification.where(null);
        if (status != null) {
            specification = specification.and(TaskSpecifications.hasStatus(status));
        }

        if (priority != null) {
            specification = specification.and(TaskSpecifications.hasPriority(priority));
        }

        if (tag != null) {
            specification = specification.and(TaskSpecifications.hasTag(tag));
        }

        List<TaskEntity> queryResult = taskService.getAllTasks(principal, pageable, specification);

        return new ResponseEntity<>(queryResult.stream().map(taskMapper::mapTo).toList(), HttpStatus.OK);
    }

    @GetMapping(path = "/tasks/{id}")
    public ResponseEntity<TaskDto> getTaskById(Principal principal, @PathVariable Long id) {
        Optional<TaskEntity> savedTaskEntity = taskService.getTaskById(principal, id);
        return savedTaskEntity.map(taskEntity -> new ResponseEntity<>(taskMapper.mapTo(taskEntity), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
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

