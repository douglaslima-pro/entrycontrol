package edu.douglaslima.entrycontrol.domain.telefone;

import com.fasterxml.jackson.annotation.JsonBackReference;

import edu.douglaslima.entrycontrol.domain.usuario.Usuario;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_usuario_telefone")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Telefone {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "telefone_id")
	private Long id;
	@Column(columnDefinition = "CHAR(2) NOT NULL")
	private String ddd;
	@Column(length = 5, nullable = false)
	private String prefixo;
	@Column(columnDefinition = "CHAR(4) NOT NULL")
	private String sufixo;
	@ManyToOne
	@JoinColumn(name = "usuario_id")
	@JsonBackReference
	private Usuario usuario;
	@Column(nullable = false)
	private String tipo;
	
}
