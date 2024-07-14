package dev.codex.springsecurityjwt.auth;

import dev.codex.springsecurityjwt.auth.DTOs.AuthenticationRequest;
import dev.codex.springsecurityjwt.auth.DTOs.AuthenticationResponse;
import dev.codex.springsecurityjwt.auth.DTOs.RegistrationRequest;
import dev.codex.springsecurityjwt.config.JwtService;
import dev.codex.springsecurityjwt.user.Role;
import dev.codex.springsecurityjwt.user.RoleRepository;
import dev.codex.springsecurityjwt.user.User;
import dev.codex.springsecurityjwt.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegistrationRequest request) {
        final String encodedPassword = passwordEncoder.encode(request.password());
        final Role role = roleRepository.findByName("USER").orElseThrow(() -> new RuntimeException("Role not found"));
        final User user = new User(request.firstName(), request.lastName(), request.email(), encodedPassword, role);
        repository.save(user);
        final String jwtToken = jwtService.generateToken(user);
        return new AuthenticationResponse(jwtToken);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );
        User user = repository.findByEmail(request.email()).orElseThrow(() -> new RuntimeException("User not found"));
        final String jwtToken = jwtService.generateToken(user);
        return new AuthenticationResponse(jwtToken);
    }
}
