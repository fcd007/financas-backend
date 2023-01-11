package br.com.code7.financasbackend.repository;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.code7.financasbackend.model.entity.Usuarios;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UsuarioRepositoryTest {

	@Autowired
	UsuarioRepository usuarioRepository;

	@Test
	public void deveVerificarExistenciaDeEmail() {
		//cenario de teste
		Usuarios usuario = Usuarios.builder().nome("Barry Allen").email("usuario@mail.com").build();
		usuarioRepository.save(usuario);
		
		//ação / execução teste
		Boolean resultadoExiste = usuarioRepository.existsByEmail(usuario.getEmail());
		
		//verificação do teste
		Assertions.assertThat(resultadoExiste).isTrue();
	}
}
