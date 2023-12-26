package br.com.code7.financasbackend.config.authentication;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.code7.financasbackend.config.security.JWTAuthResponse;
import br.com.code7.financasbackend.model.dto.LoginDto;
import br.com.code7.financasbackend.resources.controller.IAuthControllerRest;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping(IAuthControllerRest.V1_PATH)
public class AuthController implements IAuthControllerRest {
	
	private AuthService authService;

	//construir Login REST API
	@PostMapping(value = LOGIN)
    public ResponseEntity<JWTAuthResponse> authenticate(@RequestBody LoginDto loginDto){
        String token = authService.login(loginDto);

        JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
        jwtAuthResponse.setAccessToken(token);

        return ResponseEntity.ok(jwtAuthResponse);
    }
}
