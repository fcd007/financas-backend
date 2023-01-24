package br.com.code7.financasbackend.core.usuario;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsuarioController {

	@GetMapping("/")
	public String helloWorld() {
		return "Hello World";
	}
}
