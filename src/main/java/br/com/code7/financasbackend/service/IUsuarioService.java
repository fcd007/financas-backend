package br.com.code7.financasbackend.service;

import br.com.code7.financasbackend.model.entity.Usuarios;

public interface IUsuarioService {

	Usuarios autenticar(String email, String senha);

	Usuarios salvarUsuario(Usuarios usuario);

	Boolean validarEmail(String email);
}
