package br.com.code7.financasbackend.core.usuario;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.code7.financasbackend.exceptions.ErroAutenticacaoException;
import br.com.code7.financasbackend.exceptions.RegraNegocioException;
import br.com.code7.financasbackend.model.entity.Usuario;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UsuarioServiceImpl implements IUsuarioService {

	private UsuarioRepository usuarioRepository;

	public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}

	@Override
	public Usuario autenticar(String email, String senha) {
		Optional<Usuario> usuario = usuarioRepository.findByEmail(email);

		if (!usuario.isPresent()) {
			throw new ErroAutenticacaoException("Usuário não encontrado para o email informado.");
		}

		if (!usuario.get().getSenha().equals(senha)) {
			throw new ErroAutenticacaoException("Senha inválida, tente novamente.");
		}

		return usuario.get();
	}

	@Override
	@Transactional
	public Usuario salvarUsuario(Usuario usuario) {
		validarEmail(usuario.getEmail());
		return usuarioRepository.save(usuario);
	}

	@Override
	public Boolean validarEmail(String email) {
		Boolean resultado = usuarioRepository.existsByEmail(email);

		if (resultado) {
			throw new RegraNegocioException("Já existe usuário cadastrado com este e-mail.");
		}
		return resultado;
	}

	@Override
	public Optional<Usuario> buscarUsuarioPorId(Long id) {
		Optional<Usuario> usuario = null;

		usuario = usuarioRepository.findById(id);

		return usuario;
	}

}
