package edu.douglaslima.entrycontrol.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import edu.douglaslima.entrycontrol.domain.perfil.Perfil;
import edu.douglaslima.entrycontrol.domain.perfil.PerfilEnum;
import edu.douglaslima.entrycontrol.domain.telefone.Telefone;
import edu.douglaslima.entrycontrol.domain.telefone.TelefoneMapper;
import edu.douglaslima.entrycontrol.domain.usuario.Usuario;
import edu.douglaslima.entrycontrol.domain.usuario.UsuarioDTO;
import edu.douglaslima.entrycontrol.domain.usuario.UsuarioMapper;
import edu.douglaslima.entrycontrol.repository.PerfilRepository;
import edu.douglaslima.entrycontrol.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private UsuarioMapper usuarioMapper;

	@Autowired
	private TelefoneMapper telefoneMapper;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private PerfilRepository perfilRepository;
	
	public Usuario pesquisarUsuarioAtual() {
		Authentication userAuthentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = (UserDetails) userAuthentication.getPrincipal();
		return usuarioRepository.findByUsuario(userDetails.getUsername()).get();
	}
	
	public Usuario pesquisarUsuarioPeloId(Long id) throws NoSuchElementException {
		return usuarioRepository.findById(id)
				.orElseThrow(() -> new NoSuchElementException(String.format("Nenhum usuário foi encontrado com o ID %d", id)));
	}
	
	public List<Usuario> pesquisarTodos() {
		return usuarioRepository.findAll();
	}
	
	public Usuario atualizarUsuarioAtual(UsuarioDTO usuarioDTO) throws UnsupportedOperationException {
		Authentication userAuthentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = (UserDetails) userAuthentication.getPrincipal();
		Usuario usuario = usuarioRepository.findByUsuario(userDetails.getUsername()).get();
		if (contemPerfilAdmin(usuario)) {
			throw new UnsupportedOperationException("Impossível alterar os dados do usuário 'ADMIN'.");
		}
		usuarioMapper.updateUsuarioFromUsuarioDTO(usuarioDTO, usuario);
		if (usuarioDTO.telefones() != null ) {
			List<Telefone> telefones = usuarioDTO.telefones()
					.stream()
					.map(telefone -> telefoneMapper.toEntity(telefone))
					.toList();
			usuario.setTelefones(telefones);
		}
		if (usuarioDTO.senha() != null) {
			usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
		}
		usuarioRepository.save(usuario);
		return usuario;
	}
	
	public Usuario atualizarUsuarioPeloId(UsuarioDTO usuarioDTO, Long id) throws NoSuchElementException, UnsupportedOperationException {
		Usuario usuario = usuarioRepository.findById(id)
				.orElseThrow(() -> new NoSuchElementException(String.format("Nenhum usuário foi encontrado com o ID %d", id)));
		if (contemPerfilAdmin(usuario)) {
			throw new UnsupportedOperationException("Impossível alterar os dados do usuário 'ADMIN'.");
		}
		usuarioMapper.updateUsuarioFromUsuarioDTO(usuarioDTO, usuario);
		if (usuarioDTO.telefones() != null ) {
			List<Telefone> telefones = usuarioDTO.telefones()
					.stream()
					.map(telefone -> telefoneMapper.toEntity(telefone))
					.toList();
			usuario.setTelefones(telefones);
		}
		if (usuarioDTO.senha() != null) {
			usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
		}
		usuarioRepository.save(usuario);
		return usuario;
	}
	
	public void excluirUsuarioPeloId(Long id) throws NoSuchElementException, UnsupportedOperationException {
		Optional<Usuario> usuario = usuarioRepository.findById(id);
		if (usuario.isEmpty()) {
			throw new NoSuchElementException(String.format("Nenhum usuário foi encontrado com o ID %d", id));
		}
		if (contemPerfilAdmin(usuario.get())) {
			throw new UnsupportedOperationException("Impossível excluir o usuário 'ADMIN'.");
		}
		usuarioRepository.deleteById(id);
	}
	
	public boolean contemPerfilAdmin(Usuario usuario) {
		Perfil perfilAdmin = perfilRepository.findByNome(PerfilEnum.ADMIN).get();
		return usuario.contemPerfil(perfilAdmin);
	}
	
}
