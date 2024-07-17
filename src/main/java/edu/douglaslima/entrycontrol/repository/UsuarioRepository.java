package edu.douglaslima.entrycontrol.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.douglaslima.entrycontrol.domain.usuario.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	List<Usuario> findByNomeContaining(String nome);

	List<Usuario> findBySexo(String sexo);
	
	Optional<Usuario> findByUsuario(String username);
	
	Optional<Usuario> findByEmail(String email);
	
	boolean existsByUsuario(String usuario);
	
	boolean existsByEmail(String email);
	
}
