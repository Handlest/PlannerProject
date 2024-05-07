package com.example.plannerapi.repositories;
import com.example.plannerapi.domain.entities.TaskEntity;
import com.example.plannerapi.domain.entities.UserEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface TaskRepository extends JpaRepository<TaskEntity, Long>, PagingAndSortingRepository<TaskEntity, Long> {
    List<TaskEntity> getAllByTagAndUser(String tag, UserEntity user);
    List<TaskEntity> getAllByPriorityAndUser(int priority, UserEntity user);
    List<TaskEntity> getAllByUser(UserEntity user);
    Optional<TaskEntity> getByTaskIdAndUser(Long id, UserEntity user);
    List<TaskEntity> getAllByStatusAndUser(TaskEntity.Status status, UserEntity user);
    void deleteTaskByTaskId(long id);  // TODO fix task deletion
}