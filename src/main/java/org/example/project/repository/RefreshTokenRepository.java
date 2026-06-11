package org.example.project.repository;

import org.example.project.model.entity.RefreshToken;
import org.example.project.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String Token);
    Optional<RefreshToken> findByUser(User user);
}
