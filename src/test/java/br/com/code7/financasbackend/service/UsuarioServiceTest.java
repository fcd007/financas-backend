package br.com.code7.financasbackend.service;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.code7.financasbackend.core.usuario.IUsuarioService;
import br.com.code7.financasbackend.core.usuario.UsuarioRepository;
import br.com.code7.financasbackend.core.usuario.UsuarioServiceImpl;
import br.com.code7.financasbackend.exceptions.ErroAutenticacaoException;
import br.com.code7.financasbackend.exceptions.RegraNegocioException;
import br.com.code7.financasbackend.model.entity.Usuario;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class UsuarioServiceTest {

	IUsuarioService usuarioService;

	@MockBean
	UsuarioRepository usuarioRepository;

	@BeforeEach
	public void setUp() {
		usuarioService = new UsuarioServiceImpl(usuarioRepository);
	}

	public static Usuario criarUsuario() {
		return Usuario.builder().nome("Zone").email("usuario@email.com").senha("usuario").id(1L).build();

	}

	@Test
	public void deveAutenticarUsuarioCadastradoComSucessoComEmailESenhaValidados() {
		Assertions.assertDoesNotThrow(() -> {

			// cenario
			Usuario usuario = criarUsuario();
			Mockito.when(usuarioRepository.findByEmail(usuario.getEmail())).thenReturn(Optional.of(usuario));

			// acao
			Usuario resultado = usuarioService.autenticar(usuario.getEmail(), usuario.getSenha());

			// verificacao
			Assertions.assertNotNull(resultado);
		});
	}

	@Test
	public void deveLancarErroAoNaoEncontrarUsuarioOuEmailCadastrados() {
		Assertions.assertThrows(ErroAutenticacaoException.class, () -> {

			// cenario
			Mockito.when(usuarioRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());

			// acao
			Usuario resultado = usuarioService.autenticar("usuario@NaoCadastrado.com", "senhaNaoCadastrada");

			// verificacao
			Assertions.assertNull(resultado);

		});
	}

	@Test
	public void deveLancarErroQuandoSenhaDoUsuarioForInvalida() {
		Assertions.assertThrows(ErroAutenticacaoException.class, () -> {

			// cenario
			Usuario usuario = criarUsuario();
			Mockito.when(usuarioRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(usuario));

			// acao
			Usuario resultado = usuarioService.autenticar("usuario@NaoCadastrado.com", "senhaNaoCadastrada");

			// verificacao
			Assertions.assertNull(resultado);
		});
	}

	@Test
	public void deveValidarEmail() {
		Assertions.assertDoesNotThrow(() -> {

			// cenario
			Mockito.when(usuarioRepository.existsByEmail(Mockito.anyString())).thenReturn(false);

			// acao
			usuarioService.validarEmail("email@email.com");
		});
	}

	@Test
	public void deveLancarErroAoValidarEmailQuandoExistirEmailCadastrado() {
		Assertions.assertThrows(RegraNegocioException.class, () -> {

			// cenario
			Mockito.when(usuarioRepository.existsByEmail(Mockito.anyString())).thenReturn(true);

			// acao
			usuarioService.validarEmail("email@email.com");
		});
	}
}
