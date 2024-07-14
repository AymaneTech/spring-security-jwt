package dev.codex.springsecurityjwt.user.Domain;

import org.springframework.data.jpa.repository.JpaRepository;

interface RoleRepository extends JpaRepository<Role, Integer> {
}
