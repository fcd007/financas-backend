package br.com.code7.financasbackend.core.lancamento;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.code7.financasbackend.core.usuario.IUsuarioService;
import br.com.code7.financasbackend.exceptions.RegraNegocioException;
import br.com.code7.financasbackend.model.dto.LancamentoDTOV1;
import br.com.code7.financasbackend.model.entity.Lancamento;
import br.com.code7.financasbackend.model.entity.Usuario;
import br.com.code7.financasbackend.resources.controller.ILancamentoControllerRest;

@RestController
@RequestMapping(ILancamentoControllerRest.V1_PATH)
public class LancamentoController implements ILancamentoControllerRest {

	private ILancamentoService lancamentoService;

	private IUsuarioService usuarioService;

	public LancamentoController(ILancamentoService lancamentoService) {
		this.lancamentoService = lancamentoService;
	}

	@Override
	@PostMapping(value = SAVE)
	public ResponseEntity<?> save(@RequestBody LancamentoDTOV1 lancamentoDTOV1) {

		Lancamento lancamento = new Lancamento();

		try {
			// buscar usuario por id
			if (lancamentoDTOV1 != null && lancamentoDTOV1.getUsuario() != null) {
				Usuario usuario = usuarioService.buscarUsuarioPorId(lancamentoDTOV1.getUsuario())
						.orElseThrow(() -> new RegraNegocioException("Usuário não encontrado com id informado."));

				lancamento.setUsuario(usuario);
			}

			lancamento = LancamentoMapperV1.mapDtoToLancamento(lancamentoDTOV1);

			lancamento = lancamentoService.salvar(lancamento);

			return new ResponseEntity<>(lancamento, HttpStatus.CREATED);
		} catch (RegraNegocioException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@Override
	@PutMapping(value = UPDATE + "/{id}")
	public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody LancamentoDTOV1 lancamentoDTOV1) {

		try {
			return lancamentoService.buscarLancamentoPorId(id).map(entity -> {
				Lancamento lancamento = LancamentoMapperV1.mapDtoToLancamento(lancamentoDTOV1);
				lancamento.setId(entity.getId());
				lancamentoService.atualizar(lancamento);

				return new ResponseEntity<>(lancamento, HttpStatus.OK);
			}).orElseGet(
					() -> new ResponseEntity("Lançamento não encontrado na base de dados", HttpStatus.BAD_REQUEST));
		} catch (RegraNegocioException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@Override
	@DeleteMapping(value = DELETE + "/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {

		try {
			return lancamentoService.buscarLancamentoPorId(id).map(entity -> {

				lancamentoService.deletar(entity);

				return new ResponseEntity<>(entity, HttpStatus.NO_CONTENT);
			}).orElseGet(
					() -> new ResponseEntity("Lançamento não encontrado na base de dados", HttpStatus.BAD_REQUEST));
		} catch (RegraNegocioException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

}
