package com.example.plannerapi.security;
import com.example.plannerapi.domain.dto.requests.UserRefreshTokenRequest;
import com.example.plannerapi.domain.dto.requests.UserSignInRequest;
import com.example.plannerapi.domain.dto.requests.UserSignUpRequest;
import com.example.plannerapi.domain.dto.responces.UserJwtAuthenticationResponse;
import com.example.plannerapi.domain.entities.UserEntity;
import com.example.plannerapi.exceptions.UnauthorizedException;
import com.example.plannerapi.domain.entities.TokenRedisEntity;
import com.example.plannerapi.repositories.TokenRepository;
import com.example.plannerapi.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    @Value("${application.security.jwt.expiration}")
    private long accessExpTime;
    @Value("${application.security.jwt.refresh-token.expiration}")
    private long refreshExpTime;

    public UserJwtAuthenticationResponse register(UserSignUpRequest request) {
        UserEntity user = userService.create(UserEntity.builder()
                .username(request.getUsername().strip())
                .email(request.getEmail().strip())
                .role(UserEntity.Role.USER)
                .registrationDateTime(LocalDateTime.now())
                .password(passwordEncoder.encode(request.getPassword()))
                .isActive(true)
                .build());

        return generateTokenResponse(user);
    }

    public UserJwtAuthenticationResponse authenticate(UserSignInRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()));
        UserEntity user = userService.getByUsername(request.getUsername())
                .orElseThrow(() -> new UnauthorizedException("User with username " + request.getUsername() + " was not found"));

        return generateTokenResponse(user);
    }

    public UserJwtAuthenticationResponse refreshToken(UserRefreshTokenRequest request) {
        String oldRefreshToken = request.getRefreshToken();

        TokenRedisEntity token = tokenRepository.getByToken(oldRefreshToken)
                .orElseThrow(() -> new UnauthorizedException("Token is invalid or expired"));

        if (!token.getTokenType().equals("REFRESH")) {
            throw new UnauthorizedException("Invalid token type. Expected REFRESH. Found " + token.getTokenType());
        }

        String username = jwtService.extractUsername(oldRefreshToken);
        if (username == null) {
            throw new UnauthorizedException("Provided refresh token is invalid");
        }

        UserEntity user = userService.getByUsername(username)
                .orElseThrow(() -> new UnauthorizedException("Holder of token was not found"));

        tokenRepository.deleteAllByUserId(user.getUserId().toString());
        return generateTokenResponse(user);
    }

    private UserJwtAuthenticationResponse generateTokenResponse(UserEntity user) {
        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        tokenRepository.save(new TokenRedisEntity(accessToken, accessExpTime, user.getUserId().toString() ,  "ACCESS"));
        tokenRepository.save(new TokenRedisEntity(refreshToken,refreshExpTime, user.getUserId().toString(), "REFRESH"));

        return UserJwtAuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
