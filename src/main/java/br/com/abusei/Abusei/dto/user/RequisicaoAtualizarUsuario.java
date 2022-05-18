package br.com.abusei.Abusei.dto.user;

import javax.validation.constraints.NotBlank;

public class RequisicaoAtualizarUsuario {
	
	@NotBlank
	private String nome;
	
	@NotBlank
	private String sobrenome;
	
	private String descricao;
	
	@NotBlank
	private String cidade;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	
	
}
