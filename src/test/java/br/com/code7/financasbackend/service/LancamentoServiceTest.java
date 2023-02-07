package br.com.code7.financasbackend.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.Example;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.code7.financasbackend.core.lancamento.ILancamentoService;
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

	@Test
	public void deveAtualizarLancamento() {
		// cenario
		Lancamento lancamentoSalvo = criarlancamento();
		lancamentoSalvo.setStatus(StatusLancamento.PENDENTE);

		Mockito.doCallRealMethod().when(lancamentoService).validarLancamento(lancamentoSalvo);
		Mockito.when(lancamentoRepository.save(lancamentoSalvo)).thenReturn(lancamentoSalvo);

		// acao
		lancamentoService.atualizar(lancamentoSalvo);

		// verificacao
		Mockito.verify(lancamentoRepository, Mockito.times(1)).save(lancamentoSalvo);
	}

	@Test
	public void deveLancarErroaoTentarAtualizarLancamentoSemEstaSalvo() {
		// cenario
		Lancamento lancamentoSalvo = new Lancamento();
		Mockito.doThrow(NullPointerException.class).when(lancamentoService).validarLancamento(lancamentoSalvo);

		// acao
		catchThrowableOfType(() -> lancamentoService.atualizar(lancamentoSalvo), NullPointerException.class);

		// verificacao
		Mockito.verify(lancamentoRepository, Mockito.never()).save(lancamentoSalvo);
	}

	@Test
	public void deveDelatarLancamento() {
		// cenario
		Lancamento lancamento = criarlancamento();

		// acao
		lancamentoService.deletar(lancamento);

		// verificacao
		Mockito.verify(lancamentoRepository).delete(lancamento);

	}

	@Test
	public void naoDeveDelatarLancamentoSemEstaSalvo() {
		// cenario
		Lancamento lancamento = new Lancamento();
		Mockito.doThrow(NullPointerException.class).when(lancamentoService).deletar(lancamento);

		// acao
		catchThrowableOfType(() -> lancamentoService.deletar(lancamento), NullPointerException.class);

		// verificacao
		Mockito.verify(lancamentoRepository, Mockito.never()).delete(lancamento);
	}

	@Test
	public void buscarLancamentoPorFiltro() {
		// cenario
		Lancamento lancamento = criarlancamento();
		List<Lancamento> listaLancamento = Arrays.asList(lancamento);
		Mockito.when(lancamentoRepository.findAll(Mockito.any(Example.class))).thenReturn(listaLancamento);

		// acao
		List<Lancamento> resultado = lancamentoService.buscar(lancamento);

		// verificacao
		assertThat(resultado).isNotEmpty().hasSize(1).contains(lancamento);
	}

	@Test
	public void deveAtualizarStatusDeUmLancamento() {
		// cenario
		Lancamento lancamento = criarlancamento();
		StatusLancamento status = StatusLancamento.PENDENTE;
		lancamento.setStatus(status);

		Mockito.doCallRealMethod().when(lancamentoService).atualizar(lancamento);

		// acao
		StatusLancamento novoStatus = StatusLancamento.EFETIVADO;
		lancamentoService.atualizarStatus(lancamento, novoStatus);

		// verificacao
		assertThat(lancamento.getStatus()).isEqualTo(novoStatus);
		Mockito.verify(lancamentoService).atualizarStatus(lancamento, novoStatus);
	}
}
