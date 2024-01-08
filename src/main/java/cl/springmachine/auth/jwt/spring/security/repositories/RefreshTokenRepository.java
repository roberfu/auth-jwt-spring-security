package cl.springmachine.auth.jwt.spring.security.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.springmachine.auth.jwt.spring.security.entities.RefreshToken;
import cl.springmachine.auth.jwt.spring.security.entities.User;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

	Optional<RefreshToken> findByToken(String token);

	void deleteByUser(User user);
}
