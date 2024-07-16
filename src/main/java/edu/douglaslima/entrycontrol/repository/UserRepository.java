package edu.douglaslima.entrycontrol.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.douglaslima.entrycontrol.domain.usuario.Usuario;

public interface UserRepository extends JpaRepository<Usuario, Long> {

}
