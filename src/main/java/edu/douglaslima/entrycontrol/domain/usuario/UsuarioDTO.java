package edu.douglaslima.entrycontrol.domain.usuario;

import java.time.LocalDate;
import java.util.List;

import edu.douglaslima.entrycontrol.domain.endereco.Endereco;
import edu.douglaslima.entrycontrol.domain.telefone.TelefoneDTO;

public record UsuarioDTO(String nome, String bio, LocalDate dataNascimento, char sexo, String usuario, String email, String senha, List<TelefoneDTO> telefones, Endereco endereco) {

}
