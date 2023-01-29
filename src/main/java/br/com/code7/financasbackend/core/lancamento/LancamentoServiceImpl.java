package br.com.code7.financasbackend.core.lancamento;

import java.util.List;
import java.util.Objects;

import org.hibernate.criterion.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

		Lancamento lancamentoSalvo = lancamentoRepository.save(lancamento);

		return lancamentoSalvo;
	}

	@Override
	@Transactional
	public Lancamento atualizar(Lancamento lancamento) {

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
		Example example = Example.create(lancamentoFiltro).ignoreCase();

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

}
