package br.com.code7.financasbackend.repository;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.code7.financasbackend.core.lancamento.LancamentoRepository;
import br.com.code7.financasbackend.model.entity.Lancamento;
import br.com.code7.financasbackend.model.enums.StatusLancamento;
import br.com.code7.financasbackend.model.enums.TipoLancamento;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class LancamentoRepositoryTest {

	@Autowired
	LancamentoRepository lancamentoRepository;

	@Autowired
	TestEntityManager entityManager;

	@Test
	public void deveSalvarLancamento() {
		Lancamento lancamento = criarLancamento();
		
		lancamento = lancamentoRepository.save(lancamento);
		
		Assertions.assertThat(lancamento.getId()).isNotNull();
	}

	private Lancamento criarLancamento() {
		return Lancamento.builder()
				.mes(1)
				.ano(2023)
				.descricao("Lançamento teste")
				.valor(BigDecimal.valueOf(10))
				.tipo(TipoLancamento.RECEITA)
				.status(StatusLancamento.PENDENTE)
				.dataCadastro(LocalDate.now())
				.build();
	}
	
	@Test
	
	public void deveDeletarLancamento() {
		Lancamento lancamento = criarLancamento();
		
		entityManager.persist(lancamento);
		
		lancamento = entityManager.find(Lancamento.class, lancamento.getId());
		
		lancamentoRepository.delete(lancamento);
		
		Lancamento lancamentoInexistente =  entityManager.find(Lancamento.class, lancamento.getId());
		
		Assertions.assertThat(lancamentoInexistente).isNull();
	}

}
