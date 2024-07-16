package edu.douglaslima.entrycontrol.domain.usuario;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import edu.douglaslima.entrycontrol.domain.endereco.Endereco;
import edu.douglaslima.entrycontrol.domain.perfil.Perfil;
import edu.douglaslima.entrycontrol.domain.telefone.Telefone;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "usuario_id")
	private Long id;
	@Column(length = 150, nullable = false)
	private String nome;
	private String bio;
	@Column(name = "data_nascimento")
	private LocalDate dataNascimento;
	private char sexo;
	@Column(length = 30, nullable = false, unique = true)
	private String usuario;
	@Column(length = 150, nullable = false, unique = true)
	private String email;
	@Column(length = 30, nullable = false)
	private String senha;
	@OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnoreProperties("usuario")
	private List<Telefone> telefones;
	@Embedded
	private Endereco endereco;
	@ManyToMany
	@JoinTable(name = "tb_usuario_perfil", joinColumns = @JoinColumn(name = "usuario_id"), inverseJoinColumns = @JoinColumn(name = "nome"))
	private List<Perfil> perfis;
	
	public void adicionarTelefone(Telefone telefone) {
		telefone.setUsuario(this);
		telefones.add(telefone);
	}

}
