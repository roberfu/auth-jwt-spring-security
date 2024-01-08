package cl.springmachine.auth.jwt.spring.security.exceptions;

import java.net.URI;
import java.util.HashMap;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class CustomExceptionHandler {

	private static final String RFC_STRING = "https://www.rfc-editor.org/rfc/rfc7807";

	@ExceptionHandler(CustomException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public ProblemDetail globalExceptionHandler(Exception ex, WebRequest request) {
		ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR,
				ex.getMessage());
		problemDetail.setType(URI.create(RFC_STRING));
		problemDetail.setProperties(new HashMap<>());
		return problemDetail;
	}

	@ExceptionHandler(UsernameNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ProblemDetail handleNotFoundException(UsernameNotFoundException ex) {
		ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
		problemDetail.setType(URI.create(RFC_STRING));
		problemDetail.setProperties(new HashMap<>());
		return problemDetail;
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ProblemDetail handleValidationException(MethodArgumentNotValidException ex, WebRequest request) {
		String errorMessages = ex.getBindingResult().getFieldErrors().stream().map(error -> error.getDefaultMessage())
				.collect(Collectors.joining("; "));
		String body = "Validation error: " + errorMessages;
		ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, body);
		problemDetail.setType(URI.create(RFC_STRING));
		problemDetail.setProperties(new HashMap<>());
		return problemDetail;
	}

}
