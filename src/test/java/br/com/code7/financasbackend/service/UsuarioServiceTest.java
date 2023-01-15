package br.com.code7.financasbackend.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.code7.financasbackend.repository.UsuarioRepository;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class UsuarioServiceTest {

	@Autowired
	IUsuarioService usuarioService;

	@Autowired
	UsuarioRepository usuarioRepository;

	@Test
	public void deveValidarEmail() {
		Assertions.assertDoesNotThrow(() -> {
			// cenario
			usuarioRepository.deleteAll();

			// acao
			usuarioService.validarEmail("email@email.com");
		});
	}
}
