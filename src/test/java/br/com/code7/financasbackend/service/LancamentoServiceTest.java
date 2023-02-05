package br.com.code7.financasbackend.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.code7.financasbackend.core.lancamento.LancamentoRepository;
import br.com.code7.financasbackend.core.lancamento.LancamentoServiceImpl;
import br.com.code7.financasbackend.exceptions.RegraNegocioException;
import br.com.code7.financasbackend.model.entity.Lancamento;
import br.com.code7.financasbackend.model.entity.Usuario;
import br.com.code7.financasbackend.model.enums.StatusLancamento;
import br.com.code7.financasbackend.model.enums.TipoLancamento;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class LancamentoServiceTest {

	@SpyBean
	LancamentoServiceImpl lancamentoService;

	@MockBean
	LancamentoRepository lancamentoRepository;

	public static Lancamento criarlancamento() {
		Usuario usuario = new Usuario();
		usuario.setId(1L);

		return Lancamento.builder().id(1L).mes(1).ano(2023).descricao("teste").valor(BigDecimal.valueOf(10))
				.tipo(TipoLancamento.RECEITA).status(StatusLancamento.PENDENTE).dataCadastro(LocalDate.now())
				.usuario(usuario).build();
	}

	@Test
	public void deveSalvarLancamento() {
		// cenario
		Lancamento lancamentoSalvar = criarlancamento();
		Mockito.doCallRealMethod().when(lancamentoService).validarLancamento(lancamentoSalvar);

		Lancamento lancamentoSalvo = criarlancamento();
		lancamentoSalvo.setStatus(StatusLancamento.PENDENTE);
		Mockito.when(lancamentoRepository.save(lancamentoSalvar)).thenReturn(lancamentoSalvo);

		// acao
		assertDoesNotThrow(() -> {

			Lancamento lancamento = lancamentoService.salvar(lancamentoSalvar);

			// verificacao
			assertThat(lancamento.getId()).isEqualTo(lancamento.getId());
			assertThat(lancamento.getStatus()).isEqualTo(StatusLancamento.PENDENTE);
		});
	}

	@Test
	public void naoDeveSalvarlancamento() {
		// cenario
		Lancamento lancamentoSalvar = criarlancamento();
		Mockito.doThrow(RegraNegocioException.class).when(lancamentoService).validarLancamento(lancamentoSalvar);

		// acao
		catchThrowableOfType(() -> lancamentoService.salvar(lancamentoSalvar), RegraNegocioException.class);

		// verificacao
		Mockito.verify(lancamentoRepository, Mockito.never()).save(lancamentoSalvar);
	}
}
