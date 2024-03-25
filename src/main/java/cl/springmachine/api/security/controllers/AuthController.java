package cl.springmachine.api.security.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.springmachine.api.security.dtos.RefreshTokenRequestDto;
import cl.springmachine.api.security.dtos.RegisterUserRequestDto;
import cl.springmachine.api.security.dtos.TokenRequestDto;
import cl.springmachine.api.security.dtos.TokenResponseDto;
import cl.springmachine.api.security.dtos.UserResponseDto;
import cl.springmachine.api.security.exceptions.CustomException;
import cl.springmachine.api.security.services.UserJwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

	private final UserJwtService userJwtService;

	@PostMapping("/token")
	public ResponseEntity<TokenResponseDto> getToken(@Valid @RequestBody TokenRequestDto request)
			throws CustomException {
		return new ResponseEntity<>(userJwtService.getToken(request), HttpStatus.OK);
	}

	@PostMapping("/register")
	public ResponseEntity<UserResponseDto> registerUser(@Valid @RequestBody RegisterUserRequestDto request)
			throws CustomException {
		return new ResponseEntity<>(userJwtService.registerUser(request), HttpStatus.CREATED);
	}

	@PostMapping("/refresh")
	public ResponseEntity<TokenResponseDto> refreshToken(@Valid @RequestBody RefreshTokenRequestDto request) {
		return new ResponseEntity<>(userJwtService.refreshToken(request), HttpStatus.OK);
	}
}
