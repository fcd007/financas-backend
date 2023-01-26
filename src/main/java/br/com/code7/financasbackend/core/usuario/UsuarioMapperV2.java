package br.com.code7.financasbackend.core.usuario;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import br.com.code7.financasbackend.model.dto.UsuarioDTOV1;
import br.com.code7.financasbackend.model.entity.Usuario;

@Mapper
public interface UsuarioMapperV2 {

	UsuarioMapperV2 MAPPER = Mappers.getMapper(UsuarioMapperV2.class);

	UsuarioDTOV1 mapperUsuarioToDto(Usuario usuario);

	Usuario mapperDtoToUsuario(UsuarioDTOV1 dto);
}
