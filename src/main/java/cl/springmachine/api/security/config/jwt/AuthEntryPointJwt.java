package cl.springmachine.api.security.config.jwt;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {

		ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED,
				authException.getMessage());
		problemDetail.setTitle("Unauthorized");
		problemDetail.setType(URI.create("https://springmachine.cl/errors/unauthorized"));
		problemDetail.setInstance(URI.create(request.getRequestURI()));
		problemDetail.setProperties(new HashMap<>());

		response.setContentType(MediaType.APPLICATION_PROBLEM_JSON_VALUE);
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.getWriter().write(new ObjectMapper().writeValueAsString(problemDetail));

	}

}
