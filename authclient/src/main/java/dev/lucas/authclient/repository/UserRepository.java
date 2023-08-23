package dev.lucas.authclient.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import dev.lucas.authclient.model.user.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
}
