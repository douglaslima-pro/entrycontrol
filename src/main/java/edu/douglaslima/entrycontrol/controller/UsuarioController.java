package edu.douglaslima.entrycontrol.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.douglaslima.entrycontrol.domain.usuario.Usuario;
import edu.douglaslima.entrycontrol.domain.usuario.UsuarioDTO;
import edu.douglaslima.entrycontrol.service.UsuarioService;

@RestController
@RequestMapping("/user")
public class UsuarioController {
	
	@Autowired
	private UsuarioService usuarioService;

	@GetMapping("/search/me")
	public ResponseEntity<Usuario> pesquisarUsuarioAtual() {
		Usuario usuario = usuarioService.pesquisarUsuarioAtual();
		return ResponseEntity.ok(usuario);
	}

	@GetMapping("/search/{id}")
	public ResponseEntity<Usuario> pesquisarUsuarioPeloId(@PathVariable Long id) {
		Usuario usuario = usuarioService.pesquisarUsuarioPeloId(id);
		return ResponseEntity.ok(usuario);
	}
	
	@GetMapping("/search/all")
	public ResponseEntity<List<Usuario>> pesquisarTodos() {
		List<Usuario> usuarios = usuarioService.pesquisarTodos();
		return ResponseEntity.ok(usuarios);
	}
	
	@PutMapping("/update/me")
	public ResponseEntity<Usuario> atualizarUsuarioAtual(@RequestBody UsuarioDTO usuarioDTO) {
		Usuario usuario = usuarioService.atualizarUsuarioAtual(usuarioDTO);
		return ResponseEntity.ok(usuario);
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<Usuario> atualizarUsuarioPeloId(@RequestBody UsuarioDTO usuarioDTO, @PathVariable Long id) {
		Usuario usuario = usuarioService.atualizarUsuarioPeloId(usuarioDTO, id);
		return ResponseEntity.ok(usuario);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> excluirUsuarioPeloId(@PathVariable Long id) {
		usuarioService.excluirUsuarioPeloId(id);
		return ResponseEntity.noContent().build();
	}
	
}
