package dev.codex.springsecurityjwt.token;

import dev.codex.springsecurityjwt.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "tokens")
public class Token {

    @Enumerated(EnumType.STRING)
    public TokenType tokenType = TokenType.BEARER;
    public boolean revoked;
    public boolean expired;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    public User user;
    @Id
    @GeneratedValue
    private Integer id;
    @Column(nullable = false, unique = true)
    private String token;

    public Token(String token, User user, TokenType tokenType, boolean expired, boolean revoked) {
        this.token = token;
        this.user = user;
        this.tokenType = tokenType;
        this.expired = expired;
        this.revoked = revoked;
    }
}
