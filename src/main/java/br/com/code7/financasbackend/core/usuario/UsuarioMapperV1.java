package br.com.code7.financasbackend.core.usuario;

import br.com.code7.financasbackend.model.dto.UsuarioDTOV1;
import br.com.code7.financasbackend.model.entity.Usuario;

public class UsuarioMapperV1 {

	// Converter Usuario JPA Entity para usuarioDTOV1
	public static UsuarioDTOV1 mapToUserDto(Usuario usuario) {
		UsuarioDTOV1 usuarioDto = new UsuarioDTOV1(usuario.getId(), usuario.getNome(), usuario.getEmail(),
				usuario.getSenha());

		return usuarioDto;
	}

	// Converter usuarioDTOV1 para Usuario JPA Entity
	public static Usuario mapToUser(UsuarioDTOV1 usuarioDto) {
		Usuario usuario = new Usuario(usuarioDto.getId(), usuarioDto.getNome(), usuarioDto.getEmail(),
				usuarioDto.getSenha());

		return usuario;
	}
}
