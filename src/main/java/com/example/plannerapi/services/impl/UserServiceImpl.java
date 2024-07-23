package com.example.plannerapi.services.impl;

import com.example.plannerapi.domain.dto.requests.UserUpdateRequest;
import com.example.plannerapi.domain.entities.UserEntity;
import com.example.plannerapi.repositories.UserRepository;
import com.example.plannerapi.repositories.TokenRepository;
import com.example.plannerapi.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository;

    @Override
    public UserEntity save(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }

    @Override
    public UserEntity create(UserEntity userEntity) {
        if (userRepository.existsByUsername(userEntity.getUsername())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with " + userEntity.getUsername() + " username already exists");
        }
        if (userRepository.existsByEmail(userEntity.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with " + userEntity.getEmail() + " email already exists");
        }
        return userRepository.save(userEntity);
    }


    @Override
    public List<UserEntity> getAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<UserEntity> getById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<UserEntity> getByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public UserEntity update(long userId, UserUpdateRequest userUpdateRequest) {
        return userRepository.findById(userId).map(existingUserEntity -> {
            Optional.ofNullable(userUpdateRequest.getUsername()).ifPresent(existingUserEntity::setUsername);
            Optional.ofNullable(userUpdateRequest.getEmail()).ifPresent(existingUserEntity::setEmail);
            Optional.ofNullable(userUpdateRequest.getPassword()).ifPresent(pwd -> existingUserEntity.setPassword(passwordEncoder.encode(pwd)));
            Optional.ofNullable(userUpdateRequest.getSettings()).ifPresent(existingUserEntity::setSettings);
            userRepository.save(existingUserEntity);
            return existingUserEntity;
        }).orElseThrow(() -> new RuntimeException("User does not exist!"));
    }

    @Override
    public void deleteById(Long id) {
        userRepository.findById(id).ifPresent(user -> {
            tokenRepository.deleteAll(tokenRepository.findAllByUserId(user.getUserId().toString()));
            userRepository.delete(user);
        });
    }

    @Override
    public void deleteByUsername(String username){
        userRepository.findByUsername(username).ifPresent(userRepository::delete);
    }


    public Optional<UserEntity> getCurrentUser() {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return getByUsername(username);
    }
}
