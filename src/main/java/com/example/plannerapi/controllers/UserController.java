package com.example.plannerapi.controllers;

import com.example.plannerapi.domain.dto.UserDto;
import com.example.plannerapi.domain.entities.UserEntity;
import com.example.plannerapi.mappers.Mapper;
import com.example.plannerapi.services.UserService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private UserService userService;
    private Mapper<UserEntity, UserDto> userMapper;

    private UserController(UserService userService, Mapper<UserEntity, UserDto> userMapper){
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping(path = "/users")

    public ResponseEntity<UserDto> createUser(@RequestBody UserDto user){
        UserEntity userEntity = userMapper.mapFrom(user);
        UserEntity savedEntity = userService.createUser(userEntity);
        return new ResponseEntity<>(userMapper.mapTo(savedEntity), HttpStatus.CREATED);
    }
}
