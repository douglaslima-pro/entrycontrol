package edu.douglaslima.entrycontrol.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import edu.douglaslima.entrycontrol.domain.auth.AuthenticatedResponseDTO;
import edu.douglaslima.entrycontrol.domain.auth.LoginDTO;
import edu.douglaslima.entrycontrol.domain.usuario.Usuario;
import edu.douglaslima.entrycontrol.domain.usuario.UsuarioDTO;
import edu.douglaslima.entrycontrol.service.AuthenticationService;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
	
	@Autowired
	private AuthenticationService authenticationService;

	@PostMapping("/login")
	public ResponseEntity<AuthenticatedResponseDTO> login(@RequestBody LoginDTO loginDTO) {
		AuthenticatedResponseDTO responseBody = authenticationService.login(loginDTO);
		return ResponseEntity.ok(responseBody);
	}
	
	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody UsuarioDTO usuarioDTO) {
		Usuario usuario = authenticationService.register(usuarioDTO);
		URI location = ServletUriComponentsBuilder.fromPath("/usuario")
				.path("/search/{id}")
				.buildAndExpand(usuario.getId())
				.toUri();
		return ResponseEntity.created(location).body("Usuário cadastrado com sucesso!");
	}

}
