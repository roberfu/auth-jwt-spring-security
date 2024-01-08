package cl.springmachine.auth.jwt.spring.security.security.services;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.springmachine.auth.jwt.spring.security.dtos.RefreshTokenRequestDto;
import cl.springmachine.auth.jwt.spring.security.dtos.RegisterUserRequestDto;
import cl.springmachine.auth.jwt.spring.security.dtos.TokenRequestDto;
import cl.springmachine.auth.jwt.spring.security.dtos.TokenResponseDto;
import cl.springmachine.auth.jwt.spring.security.dtos.UserResponseDto;
import cl.springmachine.auth.jwt.spring.security.entities.RefreshToken;
import cl.springmachine.auth.jwt.spring.security.entities.Role;
import cl.springmachine.auth.jwt.spring.security.entities.User;
import cl.springmachine.auth.jwt.spring.security.enums.ERole;
import cl.springmachine.auth.jwt.spring.security.exceptions.CustomException;
import cl.springmachine.auth.jwt.spring.security.exceptions.TokenRefreshException;
import cl.springmachine.auth.jwt.spring.security.repositories.RoleRepository;
import cl.springmachine.auth.jwt.spring.security.repositories.UserRepository;
import cl.springmachine.auth.jwt.spring.security.security.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserJwtService {

	private final AuthenticationManager authenticationManager;

	private final UserRepository userRepository;

	private final RoleRepository roleRepository;

	private final PasswordEncoder encoder;

	private final JwtUtils jwtUtils;

	private final RefreshTokenService refreshTokenService;

	public TokenResponseDto getToken(TokenRequestDto request) throws CustomException {

		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

		refreshTokenService.deleteByUserId(userDetails.getId());

		RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

		return new TokenResponseDto(jwt, "Bearer", refreshToken.getToken());
	}

	@Transactional
	public UserResponseDto registerUser(RegisterUserRequestDto request) throws CustomException {

		Optional<User> optional = userRepository.findByEmail(request.getEmail());

		if (optional.isPresent())
			throw new CustomException("User Exists");

		Role userRole = roleRepository.findByName(ERole.ROLE_USER)
				.orElseThrow(() -> new RuntimeException("Error: Role is not found"));

		Set<Role> roles = new HashSet<>();
		roles.add(userRole);

		User user = new User(null, request.getEmail(), encoder.encode(request.getPassword()), new HashSet<>());

		try {
			user.setRoles(roles);
			userRepository.save(user);
		} catch (Exception e) {
			throw new CustomException("Database Error");
		}

		return new UserResponseDto(user.getId(), user.getEmail(), user.getRoles().stream().map(Role::getName).toList());
	}

	public TokenResponseDto refreshToken(RefreshTokenRequestDto request) {
		String requestRefreshToken = request.getRefreshToken();

		User user = refreshTokenService.findByToken(requestRefreshToken).map(refreshTokenService::verifyExpiration)
				.map(RefreshToken::getUser).orElseThrow(() -> new TokenRefreshException("Invalid Refresh Token"));

		String newToken = jwtUtils.generateTokenFromUsername(user.getEmail());

		return new TokenResponseDto(newToken, "Bearer", requestRefreshToken);
	}

}
