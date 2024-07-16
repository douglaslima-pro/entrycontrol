package edu.douglaslima.entrycontrol.domain.usuario;

import java.time.LocalDate;
import java.util.List;

import edu.douglaslima.entrycontrol.domain.endereco.Endereco;
import edu.douglaslima.entrycontrol.domain.telefone.Telefone;

public record UsuarioDTO(String nome, String bio, LocalDate dataNascimento, char sexo, String usuario, String email, String senha, List<Telefone> telefones, Endereco endereco) {

}
