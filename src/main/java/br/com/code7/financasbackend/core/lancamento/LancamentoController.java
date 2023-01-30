package br.com.code7.financasbackend.core.lancamento;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
	@RequestMapping(value = SAVE, method = RequestMethod.POST)
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

			return new ResponseEntity<>(lancamento, HttpStatus.CREATED);
		} catch (RegraNegocioException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

}
