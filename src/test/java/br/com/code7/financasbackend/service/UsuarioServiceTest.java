package br.com.code7.financasbackend.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.code7.financasbackend.core.usuario.UsuarioRepository;
import br.com.code7.financasbackend.core.usuario.UsuarioServiceImpl;
import br.com.code7.financasbackend.exceptions.ErroAutenticacaoException;
import br.com.code7.financasbackend.exceptions.RegraNegocioException;
import br.com.code7.financasbackend.model.entity.Usuario;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class UsuarioServiceTest {

	@SpyBean
	UsuarioServiceImpl usuarioService;

	@MockBean
	UsuarioRepository usuarioRepository;

	public static Usuario criarUsuario() {
		return Usuario.builder().nome("nome").email("email@email.com").senha("usuario").id(1L).build();
	}

	@Test
	public void deveSalvarUmUsuario() {
		// cenario
		Mockito.doCallRealMethod().when(usuarioService).validarEmail(Mockito.anyString());

		Usuario usuario = Usuario.builder().id(1L).nome("nome").email("email@email.com").senha("senha").build();

		Mockito.when((usuarioRepository).save(Mockito.any(Usuario.class))).thenReturn(usuario);

		assertDoesNotThrow(() -> {
			// acao
			Usuario usuarioSalvo = usuarioService.salvarUsuario(new Usuario());

			// verificacao
			assertEquals(usuarioSalvo.getId(), 1L);
			assertEquals(usuarioSalvo.getNome(), "nome");
			assertEquals(usuarioSalvo.getEmail(), "email@email.com");
			assertEquals(usuarioSalvo.getSenha(), "senha");

		});
	}

	@Test
	public void naoDeveSalvarUsuarioComEmailJaCadastrado() {
		// cenario
		StringBuilder email = new StringBuilder("email@email.com");

		Usuario usuario = Usuario.builder().email(email.toString()).build();

		Mockito.doThrow(RegraNegocioException.class).when(usuarioService).validarEmail(email.toString());
		// acao
		assertThrows(RegraNegocioException.class, () -> {
			usuarioService.salvarUsuario(usuario);

			// verificacao
			Mockito.verify(usuarioRepository, Mockito.never()).save(usuario);
		});
	}

	@Test
	public void deveAutenticarUsuarioCadastradoComSucessoComEmailESenhaValidados() {
		// cenario
		Usuario usuario = criarUsuario();
		Mockito.when(usuarioRepository.findByEmail(usuario.getEmail())).thenReturn(Optional.of(usuario));

		assertDoesNotThrow(() -> {

			// acao
			Usuario resultado = usuarioService.autenticar(usuario.getEmail(), usuario.getSenha());

			// verificacao
			assertNotNull(resultado);
		});
	}

	@Test
	public void deveLancarErroAoNaoEncontrarUsuarioOuEmailCadastrados() {
		// cenario
		Mockito.when(usuarioRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());

		assertThrows(ErroAutenticacaoException.class, () -> {

			// acao
			Usuario resultado = usuarioService.autenticar("usuario@NaoCadastrado.com", "senhaNaoCadastrada");

			// verificacao
			assertNull(resultado);

		});
	}

	@Test
	public void deveLancarErroQuandoSenhaDoUsuarioForInvalida() {
		// cenario
		Usuario usuario = criarUsuario();
		Mockito.when(usuarioRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(usuario));

		assertThrows(ErroAutenticacaoException.class, () -> {

			// acao
			Usuario resultado = usuarioService.autenticar("usuario@NaoCadastrado.com", "senhaNaoCadastrada");

			// verificacao
			assertNull(resultado);
		});
	}

	@Test
	public void deveValidarEmail() {

		// cenario
		Mockito.when(usuarioRepository.existsByEmail(Mockito.anyString())).thenReturn(false);

		assertDoesNotThrow(() -> {

			// acao
			usuarioService.validarEmail("email@email.com");
		});
	}

	@Test
	public void deveLancarErroAoValidarEmailQuandoExistirEmailCadastrado() {
		// cenario
		Mockito.when(usuarioRepository.existsByEmail(Mockito.anyString())).thenReturn(true);

		assertThrows(RegraNegocioException.class, () -> {

			// acao
			usuarioService.validarEmail("email@email.com");
		});
	}
}
