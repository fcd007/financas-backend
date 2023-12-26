package br.com.code7.financasbackend.config.security;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.code7.financasbackend.core.usuario.UsuarioRepository;
import br.com.code7.financasbackend.model.entity.Usuario;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	private UsuarioRepository usuarioRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Usuario usuario = usuarioRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("usuário não existe com o email informado"));

		Set<SimpleGrantedAuthority> authority = usuario.getRoles().stream()
				.map((role) -> new SimpleGrantedAuthority(role.getNome())).collect(Collectors.toSet());

		return new org.springframework.security.core.userdetails.User(email, usuario.getSenha(), authority);
	}
}
