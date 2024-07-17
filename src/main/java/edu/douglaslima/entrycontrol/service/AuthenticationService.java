package edu.douglaslima.entrycontrol.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import edu.douglaslima.entrycontrol.domain.auth.AuthenticatedResponseDTO;
import edu.douglaslima.entrycontrol.domain.auth.LoginDTO;
import edu.douglaslima.entrycontrol.domain.perfil.Perfil;
import edu.douglaslima.entrycontrol.domain.perfil.PerfilEnum;
import edu.douglaslima.entrycontrol.domain.telefone.Telefone;
import edu.douglaslima.entrycontrol.domain.usuario.Usuario;
import edu.douglaslima.entrycontrol.domain.usuario.UsuarioDTO;
import edu.douglaslima.entrycontrol.repository.PerfilRepository;
import edu.douglaslima.entrycontrol.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

	@Autowired
	private final AuthenticationManager authenticationManager;
	@Autowired
	private final TokenService tokenService;
	@Autowired
	private final UsuarioRepository usuarioRepository;
	@Autowired
	private final PerfilRepository perfilRepository;
	@Autowired
	private final PasswordEncoder passwordEncoder;

	public AuthenticatedResponseDTO login(LoginDTO loginDTO) {
		UsernamePasswordAuthenticationToken userAuthentication = new UsernamePasswordAuthenticationToken(loginDTO.username(), loginDTO.password());
		Authentication userAuthenticated = authenticationManager.authenticate(userAuthentication);
		UserDetails user = (UserDetails) userAuthenticated.getPrincipal();
		String token = tokenService.generateToken(user);
		return new AuthenticatedResponseDTO(user.getUsername(), token, tokenService.getExpiration(token));
	}
	
	public Usuario register(UsuarioDTO usuarioDTO) throws IllegalArgumentException {
		if (usuarioRepository.existsByUsuario(usuarioDTO.usuario())) {
			throw new IllegalArgumentException(String.format("O nome de usuário '%s' já existe", usuarioDTO.usuario()));
		}
		if (usuarioRepository.existsByEmail(usuarioDTO.email())) {
			throw new IllegalArgumentException(String.format("O e-mail '%s' já existe", usuarioDTO.email()));
		}
		Perfil perfilUser = perfilRepository.findById(PerfilEnum.USER).get();
		List<Telefone> telefones = new ArrayList<>();
		if (usuarioDTO.telefones() != null ) {
			telefones = usuarioDTO.telefones()
					.stream()
					.map(telefoneDTO -> {
						return Telefone.builder()
								.ddd(telefoneDTO.ddd())
								.prefixo(telefoneDTO.prefixo())
								.sufixo(telefoneDTO.sufixo())
								.tipo(telefoneDTO.tipo())
								.build();
					})
					.toList();
		}
		Usuario usuario = Usuario.builder()
				.nome(usuarioDTO.nome())
				.bio(usuarioDTO.bio())
				.dataNascimento(usuarioDTO.dataNascimento())
				.sexo(usuarioDTO.sexo())
				.usuario(usuarioDTO.usuario())
				.email(usuarioDTO.email())
				.senha(passwordEncoder.encode(usuarioDTO.senha()))
				.telefones(telefones)
				.endereco(usuarioDTO.endereco())
				.perfis(perfilUser)
				.build();
		usuarioRepository.save(usuario);
		return usuario;
	}

}
