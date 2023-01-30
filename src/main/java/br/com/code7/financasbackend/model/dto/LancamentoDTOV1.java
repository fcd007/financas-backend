package br.com.code7.financasbackend.model.dto;

import java.math.BigDecimal;

import br.com.code7.financasbackend.model.enums.StatusLancamento;
import br.com.code7.financasbackend.model.enums.TipoLancamento;
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
public class LancamentoDTOV1 {

	private Long id;

	private String descricao;

	private Integer mes;

	private Integer ano;

	private BigDecimal valor;

	private Long usuario;

	private String status;

	private String tipo;

}
