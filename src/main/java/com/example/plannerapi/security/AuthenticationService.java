package com.example.plannerapi.security;
import com.example.plannerapi.domain.dto.requests.UserRefreshTokenRequest;
import com.example.plannerapi.domain.dto.requests.UserSignInRequest;
import com.example.plannerapi.domain.dto.requests.UserSignUpRequest;
import com.example.plannerapi.domain.dto.responces.UserJwtAuthenticationResponse;
import com.example.plannerapi.domain.entities.UserEntity;
import com.example.plannerapi.exceptions.UnauthorizedException;
import com.example.plannerapi.security.token.Token;
import com.example.plannerapi.security.token.TokenRepository;
import com.example.plannerapi.services.UserService;
import lombok.RequiredArgsConstructor;
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
                        request.getPassword())
        );
        UserEntity user = userService.getByUsername(request.getUsername())
                .orElseThrow(() -> new UnauthorizedException("User with username " + request.getUsername() + " was not found"));

        revokeAllUserTokens(user);
        return generateTokenResponse(user);
    }

    private void saveUserToken(UserEntity user, String jwtToken, Token.TokenType tokenType) {
        tokenRepository.save(Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(tokenType)
                .expired(false)
                .revoked(false)
                .build());
    }

    // TODO rewrite
    private void revokeAllUserTokens(UserEntity user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(Math.toIntExact(user.getUserId()));
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }


    public UserJwtAuthenticationResponse refreshToken(UserRefreshTokenRequest request) {
        final String oldRefreshToken = request.getRefreshToken();
        String type = jwtService.extractTokenType(oldRefreshToken);
        if (!type.equals("REFRESH")) {
            throw new UnauthorizedException("Invalid token type. Expected REFRESH, but found " + type);
        }

        String username = jwtService.extractUsername(oldRefreshToken);
        if (username == null) {
            throw new UnauthorizedException("Provided refresh token is invalid");
        }

        UserEntity user = userService.getByUsername(username)
                .orElseThrow(() -> new UnauthorizedException("Holder of token was not found"));

        if (jwtService.isTokenExpiredDb(oldRefreshToken)) {
            throw new UnauthorizedException("Refresh token has expired");
        }

        if (!jwtService.isTokenValid(oldRefreshToken, user) || jwtService.isTokenRevokedDb(oldRefreshToken)) {
            throw new UnauthorizedException("Refresh token is invalid");
        }

        revokeAllUserTokens(user);
        return generateTokenResponse(user);
    }

    private UserJwtAuthenticationResponse generateTokenResponse(UserEntity user) {
        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(user, accessToken, Token.TokenType.BEARER);
        saveUserToken(user, refreshToken, Token.TokenType.REFRESH);

        return UserJwtAuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
