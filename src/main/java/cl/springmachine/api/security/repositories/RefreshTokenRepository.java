package cl.springmachine.api.security.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.springmachine.api.security.entities.RefreshToken;
import cl.springmachine.api.security.entities.User;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

	Optional<RefreshToken> findByToken(String token);

	void deleteByUser(User user);
}
