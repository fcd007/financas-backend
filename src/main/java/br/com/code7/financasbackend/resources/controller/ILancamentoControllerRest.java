package br.com.code7.financasbackend.resources.controller;

import org.springframework.http.ResponseEntity;

import br.com.code7.financasbackend.model.dto.LancamentoDTOV1;

public interface ILancamentoControllerRest {

	String V1_PATH = "api/v1/lancamentos";

	static final String SAVE = "/save";
	
	static final String UPDATE = "/update";

	ResponseEntity<?> save(LancamentoDTOV1 lancamentoDTO);
	
	ResponseEntity<?> update(Long id, LancamentoDTOV1 lancamentoDTO);

}
