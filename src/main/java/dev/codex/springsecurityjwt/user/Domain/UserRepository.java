package dev.codex.springsecurityjwt.user.Domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
}
