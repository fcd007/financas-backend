package br.com.code7.financasbackend.core.lancamento;

import java.util.List;

import org.hibernate.criterion.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.code7.financasbackend.model.entity.Lancamento;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {

	List<Lancamento> findAll(Example example);

}
