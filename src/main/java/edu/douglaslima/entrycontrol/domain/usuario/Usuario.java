package edu.douglaslima.entrycontrol.domain.usuario;

import java.time.LocalDate;
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
	private List<Telefone> telefones;
	@Embedded
	private Endereco endereco;
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	@JoinTable(name = "tb_usuario_perfil", joinColumns = @JoinColumn(name = "usuario_id"), inverseJoinColumns = @JoinColumn(name = "nome"))
	private List<Perfil> perfis;

	private Usuario(UsuarioBuilder builder) {
		this.nome = builder.nome;
		this.bio = builder.bio;
		this.dataNascimento = builder.dataNascimento;
		this.sexo = builder.sexo;
		this.usuario = builder.usuario;
		this.email = builder.email;
		this.senha = builder.senha;
		if (builder.telefones != null) {
			this.telefones = builder.telefones
					.stream()
					.map(telefone -> {
						telefone.setUsuario(this);
						return telefone;
					})
					.toList();
		}
		this.endereco = builder.endereco;
		this.perfis = builder.perfis;
	}
	
	public static UsuarioBuilder builder() {
		return new UsuarioBuilder();
	}
	
	public static class UsuarioBuilder {
		
		private String nome;
		private String bio;
		private LocalDate dataNascimento;
		private char sexo;
		private String usuario;
		private String email;
		private String senha;
		private List<Telefone> telefones;
		private Endereco endereco;
		private List<Perfil> perfis;
		
		private UsuarioBuilder() {}
		
		public UsuarioBuilder nome(String nome) {
			this.nome = nome;
			return this;
		}
		
		public UsuarioBuilder bio(String bio) {
			this.bio = bio;
			return this;
		}
		
		public UsuarioBuilder dataNascimento(LocalDate dataNascimento) {
			this.dataNascimento = dataNascimento;
			return this;
		}
		
		public UsuarioBuilder sexo(char sexo) {
			this.sexo = sexo;
			return this;
		}

		public UsuarioBuilder usuario(String usuario) {
			this.usuario = usuario;
			return this;
		}
		
		public UsuarioBuilder email(String email) {
			this.email = email;
			return this;
		}
		
		public UsuarioBuilder senha(String senha) {
			this.senha = senha;
			return this;
		}

		public UsuarioBuilder telefones(List<Telefone> telefones) {
			this.telefones = telefones;
			return this;
		}

		public UsuarioBuilder endereco(Endereco endereco) {
			this.endereco = endereco;
			return this;
		}

		public UsuarioBuilder perfis(List<Perfil> perfis) {
			this.perfis = perfis;
			return this;
		}

		public UsuarioBuilder perfis(Perfil... perfis) {
			this.perfis = Arrays.asList(perfis);
			return this;
		}
		
		public Usuario build() {
			return new Usuario(this);
		}
		
	}

}
