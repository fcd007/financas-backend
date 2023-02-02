package br.com.code7.financasbackend.core.lancamento;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import br.com.code7.financasbackend.model.entity.Lancamento;
import br.com.code7.financasbackend.model.enums.StatusLancamento;

public interface ILancamentoService {

	Lancamento salvar(Lancamento lancamento);

	Lancamento atualizar(Lancamento lancamento);

	Boolean deletar(Lancamento lancamento);

	List<Lancamento> buscar(Lancamento lancamentoFiltro);

	Lancamento atualizarStatus(Lancamento lancamento, StatusLancamento status);

	void validarLancamento(Lancamento lancamento);

	Optional<Lancamento> buscarLancamentoPorId(Long id);

	BigDecimal obterSaldoPorLancamentoEUsuario(Long idUsuario);
}
