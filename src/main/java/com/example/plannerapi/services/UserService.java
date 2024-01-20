package com.example.plannerapi.services;

import com.example.plannerapi.domain.entities.UserEntity;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    UserEntity createUser(UserEntity user);
}
