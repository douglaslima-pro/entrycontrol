package edu.douglaslima.entrycontrol.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.douglaslima.entrycontrol.domain.usuario.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	Optional<Usuario> findByUsuario(String username);
	
	boolean existsByUsuario(String usuario);
	
	boolean existsByEmail(String email);
	
}
