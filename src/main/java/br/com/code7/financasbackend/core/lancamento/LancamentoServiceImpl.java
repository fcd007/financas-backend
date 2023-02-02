package br.com.code7.financasbackend.core.lancamento;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.code7.financasbackend.exceptions.RegraNegocioException;
import br.com.code7.financasbackend.model.entity.Lancamento;
import br.com.code7.financasbackend.model.enums.StatusLancamento;

@Service
public class LancamentoServiceImpl implements ILancamentoService {

	private LancamentoRepository lancamentoRepository;

	public LancamentoServiceImpl(LancamentoRepository lancamentoRepository) {
		this.lancamentoRepository = lancamentoRepository;
	}

	@Override
	@Transactional
	public Lancamento salvar(Lancamento lancamento) {

		validarLancamento(lancamento);

		lancamento.setStatus(StatusLancamento.PENDENTE);

		Lancamento lancamentoSalvo = lancamentoRepository.save(lancamento);

		return lancamentoSalvo;
	}

	@Override
	@Transactional
	public Lancamento atualizar(Lancamento lancamento) {

		validarLancamento(lancamento);

		Objects.requireNonNull(lancamento.getId());

		lancamento = lancamentoRepository.save(lancamento);

		return lancamento;
	}

	@Override
	@Transactional
	public Boolean deletar(Lancamento lancamento) {

		try {

			Objects.requireNonNull(lancamento.getId());

			lancamentoRepository.delete(lancamento);

			return true;

		} catch (Exception e) {
			return false;
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<Lancamento> buscar(Lancamento lancamentoFiltro) {
		Example example = Example.of(lancamentoFiltro,
				ExampleMatcher.matching().withIgnoreCase().withStringMatcher(StringMatcher.CONTAINING));

		List<Lancamento> lista = lancamentoRepository.findAll(example);

		return lista;
	}

	@Override
	@Transactional
	public Lancamento atualizarStatus(Lancamento lancamento, StatusLancamento status) {

		Objects.requireNonNull(lancamento.getId());

		lancamento.setStatus(status);

		lancamento = lancamentoRepository.save(lancamento);

		return lancamento;
	}

	@Override
	public void validarLancamento(Lancamento lancamento) {

		if (lancamento.getDescricao() == null || lancamento.getDescricao().trim().isEmpty()) {
			throw new RegraNegocioException("Informe uma descrição para o lançamento.");
		}

		if (lancamento.getMes() == null || lancamento.getMes() < 1 || lancamento.getMes() > 12) {
			throw new RegraNegocioException("Informe um mês válido.");
		}

		if (lancamento.getAno() == null || lancamento.getAno().toString().length() != 4) {
			throw new RegraNegocioException("Informe um ano válido.");
		}

		if (lancamento.getUsuario() == null || lancamento.getUsuario().getId() == null) {
			throw new RegraNegocioException("Informe um usuário válido.");
		}

		if (lancamento.getValor() == null || lancamento.getValor().compareTo(BigDecimal.ZERO) < 1) {
			throw new RegraNegocioException("Informe um valor válido.");
		}

		if (lancamento.getTipo() == null) {
			throw new RegraNegocioException("Informe um tipo de lancamento válido.");
		}
	}

	@Override
	public Optional<Lancamento> buscarLancamentoPorId(Long id) {

		Optional<Lancamento> lancamento = lancamentoRepository.findById(id);

		return lancamento;
	}

}
