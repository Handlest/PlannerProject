package com.example.plannerapi.services;

import com.example.plannerapi.domain.dto.TaskDto;
import com.example.plannerapi.domain.dto.requests.TaskCreateRequest;
import com.example.plannerapi.domain.entities.TaskEntity;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public interface TaskService {
    TaskEntity createTask(Principal principal, TaskCreateRequest taskRequest);
    Optional<TaskEntity> updateTask(Principal principal, TaskDto taskDto);
    Optional<TaskEntity> getTaskById(Principal principal, long id);
    List<TaskEntity> getAllTasks(Principal principal);
    List<TaskEntity> getAllTasksWithStatus(Principal principal, TaskEntity.Status status);
    void deleteTask(Principal principal, TaskEntity task);
    void deleteTaskById(Principal principal, long id);
}
