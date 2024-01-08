package cl.springmachine.auth.jwt.spring.security.dtos;

import java.util.List;

import cl.springmachine.auth.jwt.spring.security.enums.ERole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDto {

	private Long id;

	private String email;

	private List<ERole> roles;
}
