package br.com.code7.financasbackend.core.lancamento;

import java.util.List;
import java.util.Objects;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

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
	public List<Lancamento> buscar(Lancamento lancamentoFiltro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Lancamento atualizarStatus(Lancamento lancamento, StatusLancamento status) {

		Objects.requireNonNull(lancamento.getId());

		lancamento.setStatus(status);

		lancamento = lancamentoRepository.save(lancamento);

		return lancamento;
	}

}
