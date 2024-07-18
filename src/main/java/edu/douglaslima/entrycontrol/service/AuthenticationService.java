package edu.douglaslima.entrycontrol.service;

import java.util.ArrayList;
import java.util.Arrays;
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
import edu.douglaslima.entrycontrol.domain.telefone.TelefoneMapper;
import edu.douglaslima.entrycontrol.domain.usuario.Usuario;
import edu.douglaslima.entrycontrol.domain.usuario.UsuarioDTO;
import edu.douglaslima.entrycontrol.domain.usuario.UsuarioMapper;
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
	@Autowired
	private final UsuarioMapper usuarioMapper;
	@Autowired
	private final TelefoneMapper telefoneMapper;

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
		Usuario usuario = usuarioMapper.toEntity(usuarioDTO);
		usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
		usuario.setPerfis(Arrays.asList(perfilUser));
		usuarioRepository.save(usuario);
		return usuario;
	}

}
