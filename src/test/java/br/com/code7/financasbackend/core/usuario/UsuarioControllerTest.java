package br.com.code7.financasbackend.core.usuario;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.code7.financasbackend.core.lancamento.LancamentoServiceImpl;
import br.com.code7.financasbackend.model.dto.UsuarioDTOV1;
import br.com.code7.financasbackend.model.entity.Usuario;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(controllers = UsuarioController.class)
@AutoConfigureMockMvc
public class UsuarioControllerTest {

	static String API = "/api/v1/usuarios";
	static final String EMAIL = "email@email.com";
	static final String SENHA = "123456";
	static final MediaType JSON = MediaType.APPLICATION_JSON;

	@Autowired
	MockMvc mockMvc;

	@MockBean
	UsuarioServiceImpl usuarioService;

	@MockBean
	LancamentoServiceImpl lancamentoService;

	private Usuario criarUsuario() {
		return Usuario.builder().id(1L).nome("Usuario").email(EMAIL).senha(SENHA).build();
	}

	@Test
	public void deveAutenticarUmUsuario() throws Exception {
		// cenario
		UsuarioDTOV1 dto = UsuarioDTOV1.builder().email(EMAIL).senha(SENHA).build();
		Usuario usuario = criarUsuario();
		Mockito.when(usuarioService.autenticar(EMAIL, SENHA)).thenReturn(usuario);
		String json = new ObjectMapper().writeValueAsString(dto);

		// acao
		MockHttpServletRequestBuilder resquest = MockMvcRequestBuilders
				.post(API.concat("/autenticarUsuario"))
				.accept(JSON)
				.contentType(JSON)
				.content(json);

		// verificar
		mockMvc.perform(resquest).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("id").value(usuario.getId()))
				.andExpect(MockMvcResultMatchers.jsonPath("nome").value(usuario.getNome()))
				.andExpect(MockMvcResultMatchers.jsonPath("email").value(usuario.getEmail()));
	}
}
