package cl.springmachine.auth.jwt.spring.security.security.services;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.springmachine.auth.jwt.spring.security.entities.RefreshToken;
import cl.springmachine.auth.jwt.spring.security.entities.User;
import cl.springmachine.auth.jwt.spring.security.exceptions.CustomException;
import cl.springmachine.auth.jwt.spring.security.exceptions.TokenRefreshException;
import cl.springmachine.auth.jwt.spring.security.repositories.RefreshTokenRepository;
import cl.springmachine.auth.jwt.spring.security.repositories.UserRepository;

@Service
public class RefreshTokenService {

	private static Integer jwtRefreshTokenExpirationTime = 86400000;

	private final RefreshTokenRepository refreshTokenRepository;

	private final UserRepository userRepository;

	public RefreshTokenService(RefreshTokenRepository refreshTokenRepository, UserRepository userRepository) {
		this.refreshTokenRepository = refreshTokenRepository;
		this.userRepository = userRepository;
	}

	public Optional<RefreshToken> findByToken(String token) {
		return refreshTokenRepository.findByToken(token);
	}

	public RefreshToken createRefreshToken(Long userId) throws CustomException {

		RefreshToken refreshToken = new RefreshToken();

		Optional<User> optional = userRepository.findById(userId);

		if (optional.isEmpty())
			throw new CustomException(null);

		refreshToken.setUser(optional.get());
		refreshToken.setExpirationDate(Instant.now().plusMillis(jwtRefreshTokenExpirationTime));
		refreshToken.setToken(UUID.randomUUID().toString());

		refreshToken = refreshTokenRepository.save(refreshToken);
		return refreshToken;
	}

	public RefreshToken verifyExpiration(RefreshToken token) {
		if (token.getExpirationDate().compareTo(Instant.now()) < 0) {
			refreshTokenRepository.delete(token);
			throw new TokenRefreshException("Token Expired");
		}

		return token;
	}

	@Transactional
	public void deleteByUserId(Long userId) throws CustomException {
		Optional<User> optional = userRepository.findById(userId);

		if (optional.isEmpty())
			throw new CustomException(null);

		refreshTokenRepository.deleteByUser(optional.get());
	}

}
