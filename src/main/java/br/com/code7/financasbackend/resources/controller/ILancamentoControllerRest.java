package br.com.code7.financasbackend.resources.controller;

import org.springframework.http.ResponseEntity;

import br.com.code7.financasbackend.model.dto.LancamentoDTOV1;

public interface ILancamentoControllerRest {

	String V1_PATH = "api/v1/lancamentos";

	static final String SAVE = "/save";

	ResponseEntity<?> save(LancamentoDTOV1 lancamentoDTO);

}
