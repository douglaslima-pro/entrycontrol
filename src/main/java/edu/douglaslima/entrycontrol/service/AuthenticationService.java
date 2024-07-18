package edu.douglaslima.entrycontrol.service;

import java.util.Arrays;

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
		if (usuarioDTO.nome() == null) {
			throw new IllegalArgumentException("O atributo 'nome' é obrigatório!");
		} else if (usuarioDTO.usuario() == null) {
			throw new IllegalArgumentException("O atributo 'usuario' é obrigatório!");
		} else if (usuarioDTO.email() == null) {
			throw new IllegalArgumentException("O atributo 'email' é obrigatório!");
		} else if (usuarioDTO.senha() == null) {
			throw new IllegalArgumentException("O atributo 'senha' é obrigatório!");
		} else if (usuarioDTO.endereco() == null) {
			throw new IllegalArgumentException("O atributo 'endereco' é obrigatório!");
		} else if (usuarioDTO.endereco().getCep() == null) {
			throw new IllegalArgumentException("O atributo 'cep' é obrigatório!");
		} else if (usuarioDTO.endereco().getEstado() == null) {
			throw new IllegalArgumentException("O atributo 'estado' é obrigatório!");
		} else if (usuarioDTO.endereco().getCidade() == null) {
			throw new IllegalArgumentException("O atributo 'cidade' é obrigatório!");
		} else if (usuarioDTO.endereco().getLogradouro() == null) {
			throw new IllegalArgumentException("O atributo 'logradouro' é obrigatório!");
		}
		Perfil perfilUser = perfilRepository.findById(PerfilEnum.USER).get();
		Usuario usuario = usuarioMapper.toEntity(usuarioDTO);
		usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
		usuario.setPerfis(Arrays.asList(perfilUser));
		usuarioRepository.save(usuario);
		return usuario;
	}

}
