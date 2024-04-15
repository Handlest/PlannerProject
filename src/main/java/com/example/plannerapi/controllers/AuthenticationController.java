package com.example.plannerapi.controllers;
import com.example.plannerapi.domain.dto.requests.UserSignInRequest;
import com.example.plannerapi.domain.dto.requests.UserSignUpRequest;
import com.example.plannerapi.domain.dto.responces.UserJwtAuthenticationResponse;
import com.example.plannerapi.security.AuthenticationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Авторизация и аутентификация")
public class AuthenticationController {
    private final AuthenticationService service;

    @PostMapping("/register")
    @CrossOrigin(origins = "http://localhost:5173")
    public ResponseEntity<UserJwtAuthenticationResponse> register(@RequestBody @Valid UserSignUpRequest request) {
        return ResponseEntity.ok(service.register(request));
    }
    @PostMapping("/authenticate")
    public ResponseEntity<UserJwtAuthenticationResponse> authenticate(@RequestBody @Valid UserSignInRequest request) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @PostMapping("/refresh-token")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        service.refreshToken(request, response);
    }
}