package br.com.code7.financasbackend.core.usuario;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import br.com.code7.financasbackend.model.dto.UsuarioDTOV1;
import br.com.code7.financasbackend.model.entity.Usuario;

@Component
@Lazy
public class UsuarioMapperV1 {

	// Converter Usuario JPA Entity para usuarioDTOV1
	public static UsuarioDTOV1 mapUsuarioToDto(Usuario usuario) {
		UsuarioDTOV1 usuarioDto = new UsuarioDTOV1(
				usuario.getId(), 
				usuario.getNome(), 
				usuario.getEmail(),
				usuario.getSenha(),
				usuario.getVersion(),
				usuario.getDataCriacao(),
				usuario.getDataAtualizacao(),
				usuario.getRoles()
			);

		return usuarioDto;
	}

	// Converter usuarioDTOV1 para Usuario JPA Entity
	public static Usuario mapDtoToUsuario(UsuarioDTOV1 usuarioDto) {
		Usuario usuario = new Usuario(
				usuarioDto.getId(),
				usuarioDto.getNome(),
				usuarioDto.getEmail(),
				usuarioDto.getSenha(),
				usuarioDto.getVersion(),
				usuarioDto.getDataCriacao(),
				usuarioDto.getDataAtualizacao(),
				usuarioDto.getRoles()				
			);

		return usuario;
	}
}
