package br.com.code7.financasbackend.core.usuario;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.code7.financasbackend.core.lancamento.ILancamentoService;
import br.com.code7.financasbackend.exceptions.ErroAutenticacaoException;
import br.com.code7.financasbackend.exceptions.RegraNegocioException;
import br.com.code7.financasbackend.model.dto.UsuarioDTOV1;
import br.com.code7.financasbackend.model.entity.Usuario;
import br.com.code7.financasbackend.resources.controller.IUsuarioControllerRest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(IUsuarioControllerRest.V1_PATH)
@RequiredArgsConstructor
public class UsuarioController implements IUsuarioControllerRest {

	private final IUsuarioService usuarioService;

	private final ILancamentoService lancamentoService;

	@Override
	@PostMapping(value = SAVE)
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
	@PostMapping(value = AUTENTICAR)
	public ResponseEntity<?> autenticarUsuario(@RequestBody UsuarioDTOV1 dto) {

		try {
			Usuario usuarioAutenticado = usuarioService.autenticar(dto.getEmail(), dto.getSenha());

			return ResponseEntity.ok(usuarioAutenticado);
		} catch (ErroAutenticacaoException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@Override
	@GetMapping(value = BUSCAR_SALDO_BY_ID_USUARIO + "/{id}")
	public ResponseEntity<?> buscarSaldoByIdUsuario(@PathVariable("id") Long id) {

		// buscar usuario por id
		Optional<Usuario> usuario = usuarioService.buscarUsuarioPorId(id);

		if (!usuario.isPresent()) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}

		BigDecimal saldo = lancamentoService.obterSaldoPorLancamentoEUsuario(usuario.get().getId());

		return ResponseEntity.ok(saldo);
	}

}
