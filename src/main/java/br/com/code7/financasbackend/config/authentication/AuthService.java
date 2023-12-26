package br.com.code7.financasbackend.config.authentication;

import br.com.code7.financasbackend.model.dto.LoginDto;

public interface AuthService {
	String login(LoginDto loginDto);
}
