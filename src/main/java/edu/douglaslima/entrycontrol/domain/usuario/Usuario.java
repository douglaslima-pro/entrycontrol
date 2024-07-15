package edu.douglaslima.entrycontrol.domain.usuario;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_usuario")
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "user_id")
	private String id;
	
}
