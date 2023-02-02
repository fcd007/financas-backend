package br.com.code7.financasbackend.core.lancamento;

import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.code7.financasbackend.core.usuario.IUsuarioService;
import br.com.code7.financasbackend.exceptions.RegraNegocioException;
import br.com.code7.financasbackend.model.dto.LancamentoDTOV1;
import br.com.code7.financasbackend.model.entity.Lancamento;
import br.com.code7.financasbackend.model.entity.Usuario;
import br.com.code7.financasbackend.resources.controller.ILancamentoControllerRest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(ILancamentoControllerRest.V1_PATH)
@RequiredArgsConstructor
public class LancamentoController implements ILancamentoControllerRest {

	private final ILancamentoService lancamentoService;

	private final IUsuarioService usuarioService;

	@Resource
	private LancamentoMapperV1 lancamentoMapperV1;

	@Override
	@PostMapping(value = SAVE)
	public ResponseEntity<?> save(@RequestBody LancamentoDTOV1 lancamentoDTOV1) {

		Lancamento lancamento = new Lancamento();
		Usuario usuario = null;

		try {
			// buscar usuario por id
			if (lancamentoDTOV1 != null && lancamentoDTOV1.getUsuario() != null) {
				usuario = usuarioService.buscarUsuarioPorId(lancamentoDTOV1.getUsuario())
						.orElseThrow(() -> new RegraNegocioException("Usuário não encontrado com id informado."));
			}

			lancamento = LancamentoMapperV1.mapDtoToLancamento(lancamentoDTOV1);

			lancamento.setUsuario(usuario);
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

				// buscar usuario por id
				if (lancamentoDTOV1 != null && lancamentoDTOV1.getUsuario() != null) {
					Usuario usuarioBuscado = usuarioService.buscarUsuarioPorId(lancamentoDTOV1.getUsuario())
							.orElseThrow(() -> new RegraNegocioException("Usuário não encontrado com id informado."));

					lancamento.setUsuario(usuarioBuscado);
				}

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

	@Override
	@GetMapping(value = BUSCAR)
	public ResponseEntity<?> buscar(
			@RequestParam(value = "decricao", required = false) String descricao,
			@RequestParam(value = "mes", required = false) Integer mes,
			@RequestParam(value = "ano", required = false) Integer ano,
			@RequestParam(value = "usuario", required = true) Long usuario) {

		Lancamento lancamento = new Lancamento();		
		//vamos adicionar os valores de filtro
		lancamento.setDescricao(descricao);
		lancamento.setMes(mes);
		lancamento.setAno(ano);
		
		Optional<Usuario> usuarioBuscado = usuarioService.buscarUsuarioPorId(usuario);

		if (!usuarioBuscado.isPresent()) {
			return ResponseEntity.badRequest().body("Usuário não encontrado pelo id informado.");
		} else {
			lancamento.setUsuario(usuarioBuscado.get());
		}

		List<Lancamento> lancamentos = lancamentoService.buscar(lancamento);

		return new ResponseEntity<>(lancamentos, HttpStatus.OK);
	}

}
