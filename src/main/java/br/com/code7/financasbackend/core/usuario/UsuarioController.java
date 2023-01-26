package br.com.code7.financasbackend.core.usuario;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.code7.financasbackend.exceptions.ErroAutenticacaoException;
import br.com.code7.financasbackend.exceptions.RegraNegocioException;
import br.com.code7.financasbackend.model.dto.UsuarioDTOV1;
import br.com.code7.financasbackend.model.entity.Usuario;
import br.com.code7.financasbackend.resources.controller.IUsuarioControllerRest;

@RestController
@RequestMapping(IUsuarioControllerRest.V1_PATH)
public class UsuarioController implements IUsuarioControllerRest {

	private IUsuarioService usuarioService;

	public UsuarioController(IUsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	@Override
	@RequestMapping(value = SAVE, method = RequestMethod.POST)
	public ResponseEntity<?> save(@RequestBody UsuarioDTOV1 dto) {
		Usuario usuario = Usuario.builder().nome(dto.getNome()).email(dto.getEmail()).senha(dto.getSenha()).build();

		try {
			Usuario usuarioSalvo = usuarioService.salvarUsuario(usuario);

			return new ResponseEntity<>(usuarioSalvo, HttpStatus.CREATED);
		} catch (RegraNegocioException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@Override
	@RequestMapping(value = AUTENTICAR, method = RequestMethod.POST)
	public ResponseEntity<?> autenticarUsuario(@RequestBody UsuarioDTOV1 dto) {

		try {
			Usuario usuarioAutenticado = usuarioService.autenticar(dto.getEmail(), dto.getSenha());

			return ResponseEntity.ok(usuarioAutenticado);
		} catch (ErroAutenticacaoException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

}
