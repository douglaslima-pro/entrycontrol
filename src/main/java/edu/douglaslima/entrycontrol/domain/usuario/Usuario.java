package edu.douglaslima.entrycontrol.domain.usuario;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "tb_usuario")
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "usuario_id")
	private Long id;
	@Column(length = 150, nullable = false)
	private String nome;
	private String bio;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@Column(name = "data_nascimento")
	private LocalDate dataNascimento;
	@Column(columnDefinition = "CHAR(1)")
	private char sexo;
	@Column(length = 30, nullable = false, unique = true)
	private String usuario;
	@Column(length = 150, nullable = false, unique = true)
	private String email;
	@Column(nullable = false)
	private String senha;
	@OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	@Setter(AccessLevel.NONE)
	private List<Telefone> telefones;
	@Embedded
	private Endereco endereco;
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	@JoinTable(name = "tb_usuario_perfil", joinColumns = @JoinColumn(name = "usuario_id"), inverseJoinColumns = @JoinColumn(name = "nome"))
	private List<Perfil> perfis;

	public void setTelefones(List<Telefone> telefones) {
		if (this.telefones == null) {
			this.telefones = new ArrayList<>();
		} else {
			this.telefones.clear();
		}
		if (telefones == null) {
			return;
		}
		this.telefones.addAll(
				telefones
					.stream()
					.map(telefone -> {
						telefone.setUsuario(this);
						return telefone;
					})
					.toList());
	}

}
