package edu.douglaslima.entrycontrol.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.douglaslima.entrycontrol.domain.perfil.Perfil;
import edu.douglaslima.entrycontrol.domain.perfil.PerfilEnum;

public interface PerfilRepository extends JpaRepository<Perfil, Long> {
	
	Optional<Perfil> findByNome(PerfilEnum nome);

}
