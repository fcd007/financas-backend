package br.com.code7.financasbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.code7.financasbackend.model.entity.Usuarios;

public interface UsuarioRepository extends JpaRepository<Usuarios, Long> {
	
	Boolean existsByEmail(String email);

}
