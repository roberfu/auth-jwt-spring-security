package cl.springmachine.api.security.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RefreshTokenRequestDto {

	@NotBlank(message = "Refresh Token Is Required")
	@JsonProperty("refresh_token")
	private String refreshToken;
}
