package edu.douglaslima.entrycontrol.domain.endereco;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {

	@Column(columnDefinition = "CHAR(8) NOT NULL")
	private String cep;
	@Column(columnDefinition = "CHAR(2) NOT NULL")
	private String estado;
	@Column(nullable = false)
	private String cidade;
	private String bairro;
	@Column(nullable = false)
	private String logradouro;
	private String complemento;
	private int numero;
	
}
