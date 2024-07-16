package edu.douglaslima.entrycontrol.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import edu.douglaslima.entrycontrol.domain.auth.AuthenticatedResponseDTO;
import edu.douglaslima.entrycontrol.domain.auth.LoginDTO;
import edu.douglaslima.entrycontrol.domain.perfil.PerfilEnum;
import edu.douglaslima.entrycontrol.domain.usuario.Usuario;
import edu.douglaslima.entrycontrol.domain.usuario.UsuarioDTO;
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
	private final PasswordEncoder passwordEncoder;

	public AuthenticatedResponseDTO login(LoginDTO loginDTO) {
		try {
			UsernamePasswordAuthenticationToken userAuthentication = new UsernamePasswordAuthenticationToken(loginDTO.username(), loginDTO.password());
			Authentication userAuthenticated = authenticationManager.authenticate(userAuthentication);
			String token = tokenService.generateToken(userAuthenticated);
			return new AuthenticatedResponseDTO(token);
		} catch (BadCredentialsException e) {
			throw new BadCredentialsException("Nome de usuário ou senha incorretos!");
		}
	}
	
	public Usuario register(UsuarioDTO usuarioDTO) throws IllegalArgumentException {
		if (usuarioRepository.existsByUsuario(usuarioDTO.usuario())) {
			throw new IllegalArgumentException("Nome de usuário já existe!");
		}
		if (usuarioRepository.existsByEmail(usuarioDTO.email())) {
			throw new IllegalArgumentException("E-mail já existe!");
		}
		
		Usuario usuario = Usuario.builder()
				.nome(usuarioDTO.nome())
				.bio(usuarioDTO.bio())
				.dataNascimento(usuarioDTO.dataNascimento())
				.sexo(usuarioDTO.sexo())
				.usuario(usuarioDTO.usuario())
				.email(usuarioDTO.email())
				.senha(passwordEncoder.encode(usuarioDTO.senha()))
				.telefones(usuarioDTO.telefones())
				.endereco(usuarioDTO.endereco())
				.perfis(PerfilEnum.USER)
				.build();
		
		System.out.println(usuarioDTO);
		System.out.println(usuario);
		
		usuarioRepository.save(usuario);
		return usuario;
	}

}
