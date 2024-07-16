package edu.douglaslima.entrycontrol.domain.perfil;

import java.util.List;

import edu.douglaslima.entrycontrol.domain.usuario.Usuario;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_perfil")
public class Perfil {

	@Id
	@Enumerated(EnumType.STRING)
	private PerfilEnum nome;
	@ManyToMany(mappedBy = "perfis")
	private List<Usuario> usuarios;

}
