package edu.douglaslima.entrycontrol.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.douglaslima.entrycontrol.domain.telefone.Telefone;

public interface TelefoneRepository extends JpaRepository<Telefone, Long> {

}
