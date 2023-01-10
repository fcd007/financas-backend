package br.com.code7.financasbackend.service;

import org.springframework.stereotype.Service;

import br.com.code7.financasbackend.exceptions.RegraNegocioException;
import br.com.code7.financasbackend.model.entity.Usuarios;
import br.com.code7.financasbackend.repository.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements IUsuarioService {

	private UsuarioRepository usuarioRepository;

	public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}

	@Override
	public Usuarios autenticar(String email, String senha) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Usuarios salvarUsuario(Usuarios usuario) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean validarEmail(String email) {
		Boolean resultado = usuarioRepository.existsByEmail(email);
		
		if(resultado) {
			throw new RegraNegocioException("Já existe usuário cadastrado com este e-mail.");
		}
		return resultado;
	}

}
