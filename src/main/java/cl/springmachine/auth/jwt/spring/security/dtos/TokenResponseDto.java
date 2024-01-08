package cl.springmachine.auth.jwt.spring.security.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenResponseDto {

	@JsonProperty("access_token")
	private String accessToken;

	@Default
	private String type = "Bearer";
}
