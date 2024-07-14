package dev.codex.springsecurityjwt.token;

import dev.codex.springsecurityjwt.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenRepository repository;

    public void save(User user, String jwtToken) {
        final Token token = new Token(jwtToken, user, TokenType.BEARER, false, false);
        repository.save(token);
    }

    public void revokeAllUserTokens(User user) {
        repository.findAllValidTokenByUser(user.getId()).stream().parallel().forEach(token -> {
            token.setRevoked(true);
            token.setExpired(true);
            repository.save(token);
        });
    }
}
