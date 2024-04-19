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
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final UserService userService;

    @Override
    public TaskEntity createTask(Principal principal, TaskCreateRequest taskRequest) {
        UserEntity currentUser = userService.getByUsername(principal.getName())
                .orElseThrow(() -> new HttpServerErrorException(HttpStatus.FORBIDDEN));
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
    public Optional<TaskEntity> updateTask(Principal principal, TaskDto taskDto) {
        UserEntity currentUser = userService.getByUsername(principal.getName())
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
    public Optional<TaskEntity> getTaskById(Principal principal, long id) {
        UserEntity currentUser = userService.getByUsername(principal.getName())
                .orElseThrow(() -> new HttpServerErrorException(HttpStatus.FORBIDDEN));
        return taskRepository.getByTaskIdAndUser(id, currentUser);
    }

    @Override
    public List<TaskEntity> getAllTasks(Principal principal) {
        UserEntity currentUser = userService.getByUsername(principal.getName())
                .orElseThrow(() -> new HttpServerErrorException(HttpStatus.FORBIDDEN));
        return taskRepository.getAllByUser(currentUser);
    }

    @Override
    public List<TaskEntity> getAllTasksWithStatus(Principal principal, TaskEntity.Status status) {
        UserEntity currentUser = userService.getByUsername(principal.getName())
                .orElseThrow(() -> new HttpServerErrorException(HttpStatus.FORBIDDEN));
        return taskRepository.getAllByStatusAndUser(status, currentUser);
    }

    @Override
    public void deleteTask(Principal principal, TaskEntity task) {

    }

    @Override
    public void deleteTaskById(Principal principal, long id) {
        UserEntity currentUser = userService.getByUsername(principal.getName())
                .orElseThrow(() -> new HttpServerErrorException(HttpStatus.FORBIDDEN));
        taskRepository.deleteByTaskIdAndUser(id, currentUser);
    }
}
