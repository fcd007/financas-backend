package br.com.code7.financasbackend.core.lancamento;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.code7.financasbackend.model.entity.Lancamento;
import br.com.code7.financasbackend.model.enums.TipoLancamento;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {

	List<Lancamento> findAll(Example example);

	@Query(value = "select sum(l.valor) from Lancamento l join l.usuario u "
			+ " where u.id = :idUsuario and l.tipo = :tipo group by u ")
	BigDecimal obterSaldoPorLancamentoEUsuario(@Param("idUsuario") Long idUsuario, @Param("tipo") TipoLancamento tipo);
}
