package br.com.code7.financasbackend.core.lancamento;

import java.util.List;

import br.com.code7.financasbackend.model.entity.Lancamento;
import br.com.code7.financasbackend.model.enums.TipoLancamento;

public interface ILancamentoService {

	Lancamento salvar(Lancamento lancamento);

	Lancamento atualizar(Lancamento lancamento);

	Boolean deletar(Lancamento lancamento);

	List<Lancamento> buscar(Lancamento lancamentoFiltro);
	
	Lancamento atualizarStatus(Lancamento lancamento, TipoLancamento status);
}
