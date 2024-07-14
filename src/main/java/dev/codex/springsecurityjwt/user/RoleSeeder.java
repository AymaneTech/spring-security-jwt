package dev.codex.springsecurityjwt.user;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RoleSeeder implements CommandLineRunner {
    private final ObjectMapper objectMapper;
    private final RoleRepository repository;

    @Override
    public void run(String... args) throws Exception {
        if (repository.count() > 0) {
            return;
        }

        try (InputStream inputStream = TypeReference.class.getResourceAsStream("/data/role.json")) {
            TypeReference<List<Role>> typeReference = new TypeReference<List<Role>>() {
                @Override
                public Type getType() {
                    return objectMapper.getTypeFactory().constructCollectionType(List.class, Role.class);
                }
            };
            List<Role> roles = objectMapper.readValue(inputStream, typeReference);
            repository.saveAll(roles);
        } catch (Exception e) {
            System.err.println("Error seeding roles: " + e.getMessage());
        }
    }
}
