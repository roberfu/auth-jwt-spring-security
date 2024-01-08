package cl.springmachine.auth.jwt.spring.security.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenRequestDto {

	@NotBlank(message = "Email Is Required")
	private String email;

	@NotBlank(message = "Password Is Required")
	private String password;

}
