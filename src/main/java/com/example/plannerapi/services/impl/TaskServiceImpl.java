package com.example.plannerapi.services.impl;

import com.example.plannerapi.domain.dto.TaskDto;
import com.example.plannerapi.domain.dto.requests.TaskCreateRequest;
import com.example.plannerapi.domain.entities.TaskEntity;
import com.example.plannerapi.domain.entities.UserEntity;
import com.example.plannerapi.repositories.TaskRepository;
import com.example.plannerapi.services.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;

    @Override
    public TaskEntity createTask(UserEntity user, TaskCreateRequest taskRequest) {
        TaskEntity taskEntity = TaskEntity.builder()
                .title(taskRequest.getTitle())
                .description(taskRequest.getDescription())
                .startDeadline(taskRequest.getDueToStart())
                .endDeadline(taskRequest.getDueToEnd())
                .priority(taskRequest.getPriority())
                .user(user)
                .status(TaskEntity.Status.NEW)
                .createdDate(LocalDateTime.now())
                .tag(taskRequest.getTag())
                .build();
        return taskRepository.save(taskEntity);
    }

    @Override
    public Optional<TaskEntity> updateTask(TaskDto taskDto) {
        Optional<TaskEntity> savedTaskEntity = taskRepository.findById(taskDto.getTaskId());

        if (savedTaskEntity.isEmpty()) {
            return Optional.empty();
        }

        TaskEntity taskEntity = savedTaskEntity.get();
        if (taskDto.getEndDeadline() != null) {
            taskEntity.setEndDeadline(taskDto.getEndDeadline());
        }
        if (taskDto.getStartDeadline() != null) {
            taskEntity.setStartDeadline(taskDto.getStartDeadline());
        }
        if (taskDto.getTag() != null) {
            taskEntity.setTag(taskDto.getTag());
        }
        if (taskDto.getTitle() != null) {
            taskEntity.setTitle(taskDto.getTitle());
        }
        if (taskDto.getDescription() != null) {
            taskEntity.setDescription(taskDto.getDescription());
        }
        if (taskDto.getPriority() != taskEntity.getPriority()) {
            taskEntity.setPriority(taskDto.getPriority());
        }
        if (taskDto.getStatus() != taskEntity.getStatus()) {
            taskEntity.setStatus(taskDto.getStatus());
        }
        taskRepository.saveAndFlush(taskEntity);
        return Optional.of(taskEntity);
    }


    @Override
    public Optional<TaskEntity> getTaskById(long id) {
        return taskRepository.findById(id);
    }

    @Override
    public List<TaskEntity> getAllTasks(UserEntity user, Pageable pageable, Specification<TaskEntity> specification) {
        return taskRepository.findAll(specification, pageable)
                .getContent()
                .stream()
                .filter(task -> Objects.equals(task.getUser().getUserId(), user.getUserId()))
                .toList();
    }

    @Override
    public void deleteTask(Principal principal, TaskEntity task) {

    }

    @Override
    public void deleteTaskById(long id) {
        taskRepository.deleteTaskByTaskId(id);
    }
}
