package dev.codex.springsecurityjwt.auth.DTOs;

public record AuthenticationRequest(
        String email,
        String password
) {
}
