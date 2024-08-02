package com.example.plannerapi.services;

import com.example.plannerapi.domain.entities.TaskEntity;
import com.example.plannerapi.domain.entities.UserEntity;
import com.example.plannerapi.repositories.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service("taskAccessService")
@RequiredArgsConstructor
public class TaskAccessService {

    private final TaskRepository taskRepository;

    public boolean canAccessTask(UserEntity userEntity, long taskId) {
        Optional<TaskEntity> task = taskRepository.findById(taskId);
        return task.isPresent() && Objects.equals(userEntity.getUserId(), task.get().getUser().getUserId());
    }
}
