package edu.douglaslima.entrycontrol.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.douglaslima.entrycontrol.domain.usuario.Usuario;
import edu.douglaslima.entrycontrol.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public Usuario pesquisarUsuario(Long id) throws NoSuchElementException {
		return usuarioRepository.findById(id)
				.orElseThrow(() -> new NoSuchElementException(String.format("Nenhum usuário foi encontrado com o ID %d.", id)));
	}
	
	public List<Usuario> pesquisarTodos() {
		return usuarioRepository.findAll();
	}
	
}
