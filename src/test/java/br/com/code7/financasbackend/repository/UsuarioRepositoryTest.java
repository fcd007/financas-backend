package br.com.code7.financasbackend.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.code7.financasbackend.model.entity.Usuarios;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("Test")
public class UsuarioRepositoryTest {

	@Autowired
	UsuarioRepository usuarioRepository;

	@Test
	public void deveVerificarExistenciaDeEmail() {
		// cenario de teste
		Usuarios usuario = Usuarios.builder().nome("Barry Allen").email("usuario@mail.com").build();
		usuarioRepository.save(usuario);

		// ação / execução teste
		Boolean resultadoExiste = usuarioRepository.existsByEmail(usuario.getEmail());

		// verificação do teste
		Assertions.assertThat(resultadoExiste).isTrue();
	}
}
