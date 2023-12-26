package br.com.code7.financasbackend.core.role;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.code7.financasbackend.model.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
	Optional<Role> findByNome(String nome);
}
