package br.com.code7.financasbackend.core.lancamento;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.code7.financasbackend.model.entity.Lancamento;
import br.com.code7.financasbackend.model.enums.TipoLancamento;

@Service
public class LancamentoServiceImpl implements ILancamentoService {

	private LancamentoRepository lancamentoRepository;

	public LancamentoServiceImpl(LancamentoRepository lancamentoRepository) {
		this.lancamentoRepository = lancamentoRepository;
	}

	@Override
	public Lancamento salvar(Lancamento lancamento) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Lancamento atualizar(Lancamento lancamento) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean deletar(Lancamento lancamento) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Lancamento> buscar(Lancamento lancamentoFiltro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Lancamento atualizarStatus(Lancamento lancamento, TipoLancamento status) {
		// TODO Auto-generated method stub
		return null;
	}

}
