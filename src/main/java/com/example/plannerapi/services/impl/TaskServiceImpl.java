package com.example.plannerapi.services.impl;
import com.example.plannerapi.domain.dto.TaskDto;
import com.example.plannerapi.domain.dto.requests.TaskCreateRequest;
import com.example.plannerapi.domain.entities.TaskEntity;
import com.example.plannerapi.domain.entities.UserEntity;
import com.example.plannerapi.repositories.TaskRepository;
import com.example.plannerapi.services.TaskService;
import com.example.plannerapi.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final UserService userService;

    @Override
    public TaskEntity createTask(TaskCreateRequest taskRequest) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity currentUser = userService.getByUsername(username).orElse(null);
        TaskEntity taskEntity = TaskEntity.builder()
                .title(taskRequest.getTitle())
                .description(taskRequest.getDescription())
                .DueToStart(taskRequest.getDueToStart())
                .DueToEnd(taskRequest.getDueToEnd())
                .priority(taskRequest.getPriority())
                .user(currentUser)
                .status(TaskEntity.Status.NEW)
                .build();
        return taskRepository.save(taskEntity);
    }

    @Override
    public Optional<TaskEntity> updateTask(TaskDto taskDto) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity currentUser = userService.getByUsername(username)
                .orElseThrow(() -> new HttpServerErrorException(HttpStatus.FORBIDDEN));
        Optional<TaskEntity> savedTaskEntity = taskRepository.getByTaskIdAndUser(taskDto.getTaskId(), currentUser);

        if (savedTaskEntity.isEmpty()) {
            return Optional.empty();
        }

        TaskEntity taskEntity = savedTaskEntity.get();
        if (taskDto.getDueToEnd() != null){
            taskEntity.setDueToEnd(taskDto.getDueToEnd());
        }
        if (taskDto.getDueToStart() != null){
            taskEntity.setDueToStart(taskDto.getDueToStart());
        }
        if (taskDto.getTag() != null){
            taskEntity.setTag(taskDto.getTag());
        }
        if(taskDto.getTitle() != null){
            taskEntity.setTitle(taskDto.getTitle());
        }
        if(taskDto.getDescription() != null){
            taskEntity.setDescription(taskDto.getDescription());
        }
        if(taskDto.getPriority() != taskEntity.getPriority()){
            taskEntity.setPriority(taskDto.getPriority());
        }
        if(taskDto.getStatus() != taskEntity.getStatus()){
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
    public List<TaskEntity> getAllTasks() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity currentUser = userService.getByUsername(username).orElse(null);
        return taskRepository.getAllByUser(currentUser);
    }

    @Override
    public List<TaskEntity> getAllTasksWithStatus(TaskEntity.Status status) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity currentUser = userService.getByUsername(username).orElse(null);
        return taskRepository.getAllByStatusAndUser(status, currentUser);
    }

    @Override
    public void deleteTask(TaskEntity task) {
    }

    @Override
    public void deleteTaskById(long id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity currentUser = userService.getByUsername(username).orElse(null);
        taskRepository.deleteByTaskIdAndUser(id, currentUser);
    }
}
