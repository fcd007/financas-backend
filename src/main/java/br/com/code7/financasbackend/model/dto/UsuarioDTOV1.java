package br.com.code7.financasbackend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioDTOV1 {

	private Long id;

	private String nome;

	private String email;

	private String senha;

}
