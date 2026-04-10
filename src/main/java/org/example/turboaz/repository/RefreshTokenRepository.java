package org.example.turboaz.repository;

import org.example.turboaz.entity.RefreshToken;
import org.example.turboaz.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String token);

    void deleteByUser(Users user);

    void deleteByToken(String token);

    Optional<RefreshToken> findByUser(Users user);
}