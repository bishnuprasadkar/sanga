package com.bishnu.chatapplication.auth;

import com.bishnu.chatapplication.auth.dto.AuthResponse;
import com.bishnu.chatapplication.auth.dto.LoginRequest;
import com.bishnu.chatapplication.auth.dto.RegisterRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final AuthCookieService authCookieService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(
        @Valid @RequestBody RegisterRequest request,
        HttpServletResponse response
    ) {
        AuthResponse authResponse = authService.register(request);
        authCookieService.addAuthenticationCookies(response, authResponse);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void login(
        @Valid @RequestBody LoginRequest request,
        HttpServletResponse response
    ) {
        AuthResponse authResponse = authService.login(request);
        authCookieService.addAuthenticationCookies(response, authResponse);
    }

    @PostMapping("/refresh")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void refresh(
        @CookieValue(name = "refresh_token") String refreshToken,
        HttpServletResponse response
    ) {
        AuthResponse authResponse = authService.refresh(refreshToken);
        authCookieService.addAuthenticationCookies(response, authResponse);
    }

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logout(
        @CookieValue(name = "refresh_token", required = false) String refreshToken,
        HttpServletResponse response)
    {
        if (refreshToken != null && !refreshToken.isBlank()) {
            authService.logout(refreshToken);
        }
        authCookieService.clearAuthenticationCookies(response);
    }
}
