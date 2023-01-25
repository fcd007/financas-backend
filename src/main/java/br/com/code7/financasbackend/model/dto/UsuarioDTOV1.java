package br.com.code7.financasbackend.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsuarioDTOV1 {

	private String nome;

	private String email;

	private String senha;

}
