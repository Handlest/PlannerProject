package com.example.plannerapi.services;

import com.example.plannerapi.domain.dto.requests.UserUpdateRequest;
import com.example.plannerapi.domain.entities.UserEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {
    List<UserEntity> getAll();
    Optional<UserEntity> getById(Long id);
    Optional<UserEntity> getCurrentUser();
    UserEntity update(long userId, UserUpdateRequest userUpdateRequest);
    void deleteByUsername(String username);
    UserEntity create(UserEntity userEntity);
    Optional<UserEntity> getByUsername(String username);
}
