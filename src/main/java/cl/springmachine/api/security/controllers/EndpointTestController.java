package cl.springmachine.api.security.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/test")
public class EndpointTestController {

	private static final String MESSAGE_STRING = "message";

	@GetMapping("/all")
	public ResponseEntity<Map<String, String>> allAccess() {
		Map<String, String> response = new HashMap<>();
		response.put(MESSAGE_STRING, "Public Content");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/user")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<Map<String, String>> userAccess() {
		Map<String, String> response = new HashMap<>();
		response.put(MESSAGE_STRING, "User Content");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/mod")
	@PreAuthorize("hasRole('MODERATOR')")
	public ResponseEntity<Map<String, String>> moderatorAccess() {
		Map<String, String> response = new HashMap<>();
		response.put(MESSAGE_STRING, "Moderator Content");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Map<String, String>> adminAccess() {
		Map<String, String> response = new HashMap<>();
		response.put(MESSAGE_STRING, "Admin Content");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
