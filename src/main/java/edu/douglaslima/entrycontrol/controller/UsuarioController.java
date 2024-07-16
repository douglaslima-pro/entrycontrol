package edu.douglaslima.entrycontrol.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.douglaslima.entrycontrol.domain.usuario.Usuario;
import edu.douglaslima.entrycontrol.service.UsuarioService;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
	
	@Autowired
	private UsuarioService usuarioService;

	@GetMapping("/search/{id}")
	public ResponseEntity<Usuario> pesquisarUsuario(@PathVariable Long id) {
		Usuario usuario = usuarioService.pesquisarUsuario(id);
		return ResponseEntity.ok(usuario);
	}
	
	@GetMapping("/search/all")
	public ResponseEntity<List<Usuario>> pesquisarTodos() {
		List<Usuario> usuarios = usuarioService.pesquisarTodos();
		return ResponseEntity.ok(usuarios);
	}
	
}
