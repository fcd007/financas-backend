package br.com.code7.financasbackend.core.lancamento;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import br.com.code7.financasbackend.model.dto.LancamentoDTOV1;
import br.com.code7.financasbackend.model.entity.Lancamento;
import br.com.code7.financasbackend.model.enums.StatusLancamento;
import br.com.code7.financasbackend.model.enums.TipoLancamento;

@Component
@Lazy
public class LancamentoMapperV1 {

	public static LancamentoDTOV1 mapLancamentoToDto(Lancamento lancamento) {
		LancamentoDTOV1 lancamentoDTOV1 = new LancamentoDTOV1();

		lancamentoDTOV1.setId(lancamento.getId());
		lancamentoDTOV1.setDescricao(lancamento.getDescricao());
		lancamentoDTOV1.setMes(lancamento.getMes());
		lancamentoDTOV1.setDia(lancamento.getDia());
		lancamentoDTOV1.setAno(lancamento.getAno());
		lancamentoDTOV1.setValor(lancamento.getValor());
		lancamentoDTOV1.setVersion(lancamento.getVersion());
		lancamentoDTOV1.setDataCriacao(lancamento.getDataCriacao());
		lancamentoDTOV1.setDataAtualizacao(lancamento.getDataCriacao());

		if (lancamento.getStatus() != null) {
			lancamentoDTOV1.setStatus(lancamento.getStatus().descricao());
		}

		if (lancamento.getTipo() != null) {
			lancamentoDTOV1.setTipo(lancamento.getTipo().descricao());
		}

		return lancamentoDTOV1;
	}

	// Converter usuarioDTOV1 para Usuario JPA Entity
	public static Lancamento mapDtoToLancamento(LancamentoDTOV1 lancamentoDTOV1) {
		Lancamento lancamento = new Lancamento();

		lancamento.setId(lancamentoDTOV1.getId());
		lancamento.setDescricao(lancamentoDTOV1.getDescricao());
		lancamento.setDia(lancamentoDTOV1.getDia());
		lancamento.setMes(lancamentoDTOV1.getMes());
		lancamento.setAno(lancamentoDTOV1.getAno());
		lancamento.setValor(lancamentoDTOV1.getValor());
		lancamento.setVersion(lancamentoDTOV1.getVersion());
		lancamento.setDataCriacao(lancamentoDTOV1.getDataCriacao());
		lancamento.setDataAtualizacao(lancamentoDTOV1.getDataCriacao());

		if (lancamentoDTOV1.getStatus() != null) {
			lancamento.setStatus(StatusLancamento.valueOf(lancamentoDTOV1.getStatus()));
		}

		if (lancamentoDTOV1.getTipo() != null) {
			
			if("DESPESA".equalsIgnoreCase(lancamentoDTOV1.getTipo())) {
				lancamento.setTipo(TipoLancamento.DESPESA);
			}else {
				lancamento.setTipo(TipoLancamento.RECEITA);			
			}
		}

		return lancamento;
	}

}
