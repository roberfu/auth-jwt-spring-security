package cl.springmachine.api.security.dtos;

import java.util.List;

import cl.springmachine.api.security.enums.ERole;
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
