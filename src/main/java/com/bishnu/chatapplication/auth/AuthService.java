package com.bishnu.chatapplication.auth;

import com.bishnu.chatapplication.auth.dto.AuthResponse;
import com.bishnu.chatapplication.auth.dto.LoginRequest;
import com.bishnu.chatapplication.auth.dto.RegisterRequest;
import com.bishnu.chatapplication.entity.RefreshToken;
import com.bishnu.chatapplication.entity.User;
import com.bishnu.chatapplication.repository.RefreshTokenRepository;
import com.bishnu.chatapplication.repository.UserRepository;
import java.time.Duration;
import java.time.Instant;
import java.util.Locale;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final Duration refreshTokenExpiration;

    public AuthService(
        UserRepository userRepository,
        RefreshTokenRepository refreshTokenRepository,
        PasswordEncoder passwordEncoder,
        JwtService jwtService,
        @Value("${app.jwt.refresh-token-expiration}") Duration refreshTokenExpiration
    ) {
        this.userRepository = userRepository;
        this.refreshTokenRepository = refreshTokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.refreshTokenExpiration = refreshTokenExpiration;
    }

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        String username = normalizeUsername(request.username());
        if (userRepository.existsByUsername(username)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username is already in use");
        }

        User user = User.builder()
            .username(username)
            .password(passwordEncoder.encode(request.password()))
            .displayName(request.displayName())
            .build();
        return createTokens(userRepository.save(user));
    }

    @Transactional
    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(normalizeUsername(request.username()))
            .orElseThrow(this::invalidCredentials);

        if (!Boolean.TRUE.equals(user.getActive()) || !passwordEncoder.matches(request.password(), user.getPassword())) {
            throw invalidCredentials();
        }
        return createTokens(user);
    }

    @Transactional
    public AuthResponse refresh(String token) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token)
            .orElseThrow(this::invalidRefreshToken);

        if (Boolean.TRUE.equals(refreshToken.getRevoked()) || refreshToken.getExpiryDate().isBefore(Instant.now())) {
            throw invalidRefreshToken();
        }

        refreshToken.setRevoked(true);
        return createTokens(refreshToken.getUser());
    }

    @Transactional
    public void logout(String token) {
        refreshTokenRepository.findByToken(token).ifPresent(refreshToken -> refreshToken.setRevoked(true));
    }

    private AuthResponse createTokens(User user) {
        String refreshTokenValue = UUID.randomUUID().toString();
        RefreshToken refreshToken = RefreshToken.builder()
            .user(user)
            .token(refreshTokenValue)
            .expiryDate(Instant.now().plus(refreshTokenExpiration))
            .build();
        refreshTokenRepository.save(refreshToken);

        return AuthResponse.builder()
            .accessToken(jwtService.generateAccessToken(user.getUsername()))
            .refreshToken(refreshTokenValue)
            .tokenType("Bearer")
            .expiresIn(jwtService.getAccessTokenExpirationSeconds())
            .build();
    }

    private ResponseStatusException invalidCredentials() {
        return new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password");
    }

    private ResponseStatusException invalidRefreshToken() {
        return new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid or expired refresh token");
    }

    private String normalizeUsername(String username) {
        return username.toLowerCase(Locale.ROOT);
    }
}
