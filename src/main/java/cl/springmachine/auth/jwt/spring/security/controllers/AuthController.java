package cl.springmachine.auth.jwt.spring.security.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.springmachine.auth.jwt.spring.security.dtos.RegisterUserRequestDto;
import cl.springmachine.auth.jwt.spring.security.dtos.TokenRequestDto;
import cl.springmachine.auth.jwt.spring.security.dtos.TokenResponseDto;
import cl.springmachine.auth.jwt.spring.security.dtos.UserResponseDto;
import cl.springmachine.auth.jwt.spring.security.exceptions.CustomException;
import cl.springmachine.auth.jwt.spring.security.security.services.SecurityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

	private final SecurityService securityService;

	@PostMapping("/token")
	public ResponseEntity<TokenResponseDto> authenticateUser(@Valid @RequestBody TokenRequestDto request) {
		return new ResponseEntity<>(securityService.getToken(request), HttpStatus.OK);
	}

	@PostMapping("/register")
	public ResponseEntity<UserResponseDto> registerUser(@Valid @RequestBody RegisterUserRequestDto request)
			throws CustomException {
		return new ResponseEntity<>(securityService.registerUser(request), HttpStatus.CREATED);
	}
}
