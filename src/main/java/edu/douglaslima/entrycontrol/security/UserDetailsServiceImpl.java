package edu.douglaslima.entrycontrol.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import edu.douglaslima.entrycontrol.domain.usuario.Usuario;
import edu.douglaslima.entrycontrol.repository.UsuarioRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = usuarioRepository.findByUsuario(username)
				.orElseThrow(() -> new UsernameNotFoundException("O nome de usuário não existe!"));
		List<? extends GrantedAuthority> authorities = usuario.getPerfis()
				.stream()
				.map(perfil -> new SimpleGrantedAuthority("ROLE_" + perfil.getNome()))
				.toList();
		return User.builder()
				.username(usuario.getUsuario())
				.password(usuario.getSenha())
				.authorities(authorities)
				.build();
	}

}
