package br.com.code7.financasbackend.repository;

import java.util.Optional;

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

import br.com.code7.financasbackend.model.entity.Usuario;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UsuarioRepositoryTest {

	@Autowired
	UsuarioRepository usuarioRepository;

	@Autowired
	TestEntityManager entityManager;

	/**
	 * Retorna um novo usuario
	 * 
	 * @return Usuario
	 */
	public static Usuario criarUsuario() {
		return Usuario.builder().nome("usuario").senha("12345678").email("usuario@mail.com").build();
	}

	@Test
	public void deveVerificarExistenciaDeEmail() {
		// cenario de teste
		Usuario usuario = criarUsuario();
		entityManager.persist(usuario);

		// ação / execução teste
		Boolean resultadoExiste = usuarioRepository.existsByEmail(usuario.getEmail());

		// verificação do teste
		Assertions.assertThat(resultadoExiste).isTrue();
	}

	@Test
	public void deveRetornarFalsoQuandoNaoExistirUsuarioCadastradoComEmail() {
		// cenario

		// acao
		Boolean resultado = usuarioRepository.existsByEmail("usuario@mail.com");

		// verificacao
		Assertions.assertThat(resultado).isFalse();
	}

	@Test
	public void devePersistirUmUsuarioNaBasdeDeDados() {
		// cenario
		Usuario usuario = criarUsuario();

		// acao
		Usuario usuarioSalvo = usuarioRepository.save(usuario);

		// verificacao
		Assertions.assertThat(usuarioSalvo.getId()).isNotNull();
	}

	@Test
	public void deveBuscarUsuaruiPorEmail() {
		// cenario
		Usuario usuario = criarUsuario();
		entityManager.persist(usuario);

		// acao
		Optional<Usuario> resultado = usuarioRepository.findByEmail("usuario@mail.com");

		// verificacao
		Assertions.assertThat(resultado.isPresent()).isTrue();
	}
	@Test
	public void deveRetornarVazioQuandoNaoExistirEmailCadastradoNaBase() {
		// cenario
		
		// acao
		Optional<Usuario> resultado = usuarioRepository.findByEmail("usuario@mail.com");
		
		// verificacao
		Assertions.assertThat(resultado.isPresent()).isFalse();
	}
}
