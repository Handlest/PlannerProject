package com.example.plannerapi.services;

import com.example.plannerapi.domain.dto.TaskDto;
import com.example.plannerapi.domain.dto.requests.TaskCreateRequest;
import com.example.plannerapi.domain.entities.TaskEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface TaskService {
    TaskEntity createTask(TaskCreateRequest taskRequest);
    Optional<TaskEntity> updateTask(TaskDto taskDto);
    Optional<TaskEntity> getTaskById(long id);
    List<TaskEntity> getAllTasks();
    List<TaskEntity> getAllTasksWithStatus(TaskEntity.Status status);
    void deleteTask(TaskEntity task);
    void deleteTaskById(long id);
}
