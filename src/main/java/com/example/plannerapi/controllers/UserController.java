package com.example.plannerapi.controllers;

import com.example.plannerapi.domain.dto.UserDto;
import com.example.plannerapi.domain.dto.requests.UserUpdateRequest;
import com.example.plannerapi.domain.entities.UserEntity;
import com.example.plannerapi.mappers.Mapper;
import com.example.plannerapi.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final Mapper<UserEntity, UserDto> userMapper;

    @GetMapping("api/v1/current-user")
    public ResponseEntity<UserDto> getCurrentUser(Principal principal) {
        Optional<UserEntity> userEntity = userService.getByUsername(principal.getName());
        return userEntity.map(entity
                        -> new ResponseEntity<>(userMapper.mapTo(entity), HttpStatus.OK))
                .orElseGet(()
                        -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping(path = "/users")
    public ResponseEntity<UserDto> updateUser(Principal principal, @RequestBody UserUpdateRequest userUpdateRequest) {
        Optional<UserEntity> userEntity = userService.getByUsername(principal.getName());
        if (userEntity.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        UserEntity savedUserEntity = userService.update(userEntity.get().getUserId(), userUpdateRequest);
        return new ResponseEntity<>(userMapper.mapTo(savedUserEntity), HttpStatus.OK);
    }


    @DeleteMapping(path = "/users")
    public ResponseEntity<HttpStatus> deleteUser(Principal principal) {
        userService.deleteByUsername(principal.getName());
        return new ResponseEntity<>(HttpStatus.OK);
    }





    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserEntity> userEntities = userService.getAll();
        return new ResponseEntity<>(userEntities.stream().map(userMapper::mapTo).toList(), HttpStatus.OK);
    }

    @GetMapping(path = "/users/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("id") Long id) {
        Optional<UserEntity> userEntity = userService.getById(id);
        return userEntity.map(entity
                        -> new ResponseEntity<>(userMapper.mapTo(entity), HttpStatus.OK))
                .orElseGet(()
                        -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
