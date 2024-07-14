package dev.codex.springsecurityjwt.auth.DTOs;

public record RegistrationRequest(
        String firstName,
        String lastName,
        String email,
        String password
) {
}
