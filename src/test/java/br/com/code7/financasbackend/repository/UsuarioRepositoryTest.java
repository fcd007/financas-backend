package br.com.code7.financasbackend.repository;

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

	@Test
	public void deveVerificarExistenciaDeEmail() {
		// cenario de teste
		Usuario usuario = Usuario.builder().nome("Barry Allen").email("usuario@mail.com").build();
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
}
