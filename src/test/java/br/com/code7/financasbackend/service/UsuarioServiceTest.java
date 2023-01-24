package br.com.code7.financasbackend.service;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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
		return Usuario.builder().nome("Zone").email("usuario@email.com").senha("usuario").id(1L).build();
	}

	@Test
	public void deveSalvarUmUsuario() {
		// cenario

		// acao

		// verificacao

	}

	@Test
	public void deveAutenticarUsuarioCadastradoComSucessoComEmailESenhaValidados() {
		// cenario
		Usuario usuario = criarUsuario();
		Mockito.when(usuarioRepository.findByEmail(usuario.getEmail())).thenReturn(Optional.of(usuario));

		Assertions.assertDoesNotThrow(() -> {

			// acao
			Usuario resultado = usuarioService.autenticar(usuario.getEmail(), usuario.getSenha());

			// verificacao
			Assertions.assertNotNull(resultado);
		});
	}

	@Test
	public void deveLancarErroAoNaoEncontrarUsuarioOuEmailCadastrados() {
		// cenario
		Mockito.when(usuarioRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());

		Assertions.assertThrows(ErroAutenticacaoException.class, () -> {

			// acao
			Usuario resultado = usuarioService.autenticar("usuario@NaoCadastrado.com", "senhaNaoCadastrada");

			// verificacao
			Assertions.assertNull(resultado);

		});
	}

	@Test
	public void deveLancarErroQuandoSenhaDoUsuarioForInvalida() {
		// cenario
		Usuario usuario = criarUsuario();
		Mockito.when(usuarioRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(usuario));

		Assertions.assertThrows(ErroAutenticacaoException.class, () -> {

			// acao
			Usuario resultado = usuarioService.autenticar("usuario@NaoCadastrado.com", "senhaNaoCadastrada");

			// verificacao
			Assertions.assertNull(resultado);
		});
	}

	@Test
	public void deveValidarEmail() {

		// cenario
		Mockito.when(usuarioRepository.existsByEmail(Mockito.anyString())).thenReturn(false);

		Assertions.assertDoesNotThrow(() -> {

			// acao
			usuarioService.validarEmail("email@email.com");
		});
	}

	@Test
	public void deveLancarErroAoValidarEmailQuandoExistirEmailCadastrado() {
		// cenario
		Mockito.when(usuarioRepository.existsByEmail(Mockito.anyString())).thenReturn(true);

		Assertions.assertThrows(RegraNegocioException.class, () -> {

			// acao
			usuarioService.validarEmail("email@email.com");
		});
	}
}
