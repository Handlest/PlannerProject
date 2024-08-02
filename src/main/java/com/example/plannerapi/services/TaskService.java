package com.example.plannerapi.services;

import com.example.plannerapi.domain.dto.TaskDto;
import com.example.plannerapi.domain.dto.requests.TaskCreateRequest;
import com.example.plannerapi.domain.entities.TaskEntity;
import com.example.plannerapi.domain.entities.UserEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public interface TaskService {
    TaskEntity createTask(UserEntity user, TaskCreateRequest taskRequest);
    Optional<TaskEntity> updateTask(TaskDto taskDto);
    Optional<TaskEntity> getTaskById(long id);
    List<TaskEntity> getAllTasks(UserEntity user, Pageable pageable, Specification<TaskEntity> specification);
    void deleteTask(Principal principal, TaskEntity task);
    void deleteTaskById(long id);
}
