package com.example.plannerapi.services.impl;

import com.example.plannerapi.domain.entities.UserEntity;
import com.example.plannerapi.repositories.UserRepository;
import com.example.plannerapi.services.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    private UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserEntity createUser(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }
}
