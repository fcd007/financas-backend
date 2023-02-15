package br.com.code7.financasbackend.model.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
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
	
	private Long version;

	private LocalDateTime dataCriacao;

	private LocalDateTime dataAtualizacao;

}
