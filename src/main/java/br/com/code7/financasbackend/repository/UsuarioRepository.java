package br.com.code7.financasbackend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.code7.financasbackend.model.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
	Boolean existsByEmail(String email);
	
	Optional<Usuario> findByEmail(String email);
}
