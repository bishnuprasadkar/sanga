package com.bishnu.chatapplication.auth;

import java.time.Duration;

import com.bishnu.chatapplication.auth.dto.AuthResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

@Service
public class AuthCookieService {

    @Value("${app.jwt.refresh-token-expiration}")
    private Duration refreshTokenExpiration;

    public void addAuthenticationCookies(
        HttpServletResponse response,
        AuthResponse authResponse
    ) {
        addAccessTokenCookie(response, authResponse);
        addRefreshTokenCookie(response, authResponse);
    }

    private void addAccessTokenCookie(HttpServletResponse response, AuthResponse authResponse) {
        ResponseCookie cookie = ResponseCookie.from("access_token", authResponse.accessToken())
            .httpOnly(true)
            .secure(false) // Use true in production with HTTPS
            .sameSite("Lax")
            .path("/")
            .maxAge(Duration.ofSeconds(authResponse.expiresIn()))
            .build();

        response.addHeader("Set-Cookie", cookie.toString());
    }

    private void addRefreshTokenCookie(HttpServletResponse response, AuthResponse authResponse) {
        ResponseCookie cookie = ResponseCookie.from("refresh_token", authResponse.refreshToken())
            .httpOnly(true)
            .secure(false) // Change to true when using HTTPS in production
            .sameSite("Lax")
            .path("/api/auth")
            .maxAge(refreshTokenExpiration)
            .build();

        response.addHeader("Set-Cookie", cookie.toString());
    }

    public void clearAuthenticationCookies(HttpServletResponse response) {
        ResponseCookie accessTokenCookie = ResponseCookie.from("access_token", "")
            .httpOnly(true)
            .secure(false) // Change to true when using HTTPS in production
            .sameSite("Lax")
            .path("/")
            .maxAge(Duration.ZERO)
            .build();

        ResponseCookie refreshTokenCookie = ResponseCookie.from("refresh_token", "")
            .httpOnly(true)
            .secure(false) // Change to true when using HTTPS in production
            .sameSite("Lax")
            .path("/api/auth")
            .maxAge(Duration.ZERO)
            .build();

        response.addHeader("Set-Cookie", accessTokenCookie.toString());
        response.addHeader("Set-Cookie", refreshTokenCookie.toString());
    }
}
