package br.com.code7.financasbackend.service;

import br.com.code7.financasbackend.model.entity.Usuario;

public interface IUsuarioService {

	Usuario autenticar(String email, String senha);

	Usuario salvarUsuario(Usuario usuario);

	Boolean validarEmail(String email);
}
