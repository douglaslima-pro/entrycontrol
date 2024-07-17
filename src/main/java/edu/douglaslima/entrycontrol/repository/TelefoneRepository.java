package edu.douglaslima.entrycontrol.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.douglaslima.entrycontrol.domain.telefone.Telefone;
import edu.douglaslima.entrycontrol.domain.usuario.Usuario;

public interface TelefoneRepository extends JpaRepository<Telefone, Long> {

	List<Telefone> findByUsuario(Usuario usuario);
	
	List<Telefone> findByTipo(Usuario tipo);
	
	Optional<Telefone> findByDddAndPrefixoAndSufixo(String ddd, String prefixo, String sufixo);
	
}
