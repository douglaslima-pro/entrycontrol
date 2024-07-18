package edu.douglaslima.entrycontrol.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import edu.douglaslima.entrycontrol.domain.telefone.Telefone;
import edu.douglaslima.entrycontrol.domain.telefone.TelefoneMapper;
import edu.douglaslima.entrycontrol.domain.usuario.Usuario;
import edu.douglaslima.entrycontrol.domain.usuario.UsuarioDTO;
import edu.douglaslima.entrycontrol.domain.usuario.UsuarioMapper;
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
	
	public Usuario atualizarUsuarioAtual(UsuarioDTO usuarioDTO) {
		Authentication userAuthentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = (UserDetails) userAuthentication.getPrincipal();
		Usuario usuario = usuarioRepository.findByUsuario(userDetails.getUsername()).get();
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
	
	public Usuario atualizarUsuarioPeloId(UsuarioDTO usuarioDTO, Long id) throws NoSuchElementException {
		Usuario usuario = usuarioRepository.findById(id)
				.orElseThrow(() -> new NoSuchElementException(String.format("Nenhum usuário foi encontrado com o ID %d", id)));
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
	
	public void excluirUsuarioPeloId(Long id) throws NoSuchElementException {
		if (!usuarioRepository.existsById(id)) {
			throw new NoSuchElementException(String.format("Nenhum usuário foi encontrado com o ID %d", id));
		}
		usuarioRepository.deleteById(id);
	}
	
}
