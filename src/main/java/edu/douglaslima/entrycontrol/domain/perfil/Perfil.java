package edu.douglaslima.entrycontrol.domain.perfil;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import edu.douglaslima.entrycontrol.domain.usuario.Usuario;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_perfil")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Perfil {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "perfil_id")
	@JsonIgnore
	private Long id;
	@Enumerated(EnumType.STRING)
	private PerfilEnum nome;
	@ManyToMany(mappedBy = "perfis", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Usuario> usuarios;

}
