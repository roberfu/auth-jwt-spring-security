package cl.springmachine.auth.jwt.spring.security.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.springmachine.auth.jwt.spring.security.entities.Role;
import cl.springmachine.auth.jwt.spring.security.enums.ERole;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

	Optional<Role> findByName(ERole name);
}