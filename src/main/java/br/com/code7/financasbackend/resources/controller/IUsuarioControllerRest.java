package br.com.code7.financasbackend.resources.controller;

import org.springframework.http.ResponseEntity;

import br.com.code7.financasbackend.model.dto.UsuarioDTOV1;

public interface IUsuarioControllerRest {

	String V1_PATH = "api/v1/usuarios";

	static final String SAVE = "/save";
	static final String AUTENTICAR = "/autenticarUsuario";

	ResponseEntity<?> save(UsuarioDTOV1 usuarioDTOV1);

	ResponseEntity<?> autenticarUsuario(UsuarioDTOV1 usuarioDTOV1);
}
