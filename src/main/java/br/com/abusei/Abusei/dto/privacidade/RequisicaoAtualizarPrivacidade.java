package br.com.abusei.Abusei.dto.privacidade;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class RequisicaoAtualizarPrivacidade {
	@Email
	private String email;
	
	@NotBlank(message = "Campo obrigatório! Não deve estar em branco!")
	@Min(value = 8, message = "Deve ter mais de 8 caracteres!")
	private String senha;
	
	@NotBlank(message = "Campo obrigatório! Não deve estar em branco!")
	@Min(value = 8, message = "Deve ter mais de 8 caracteres!")
	private String novaSenha;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getNovaSenha() {
		return novaSenha;
	}
	public void setNovaSenha(String novaSenha) {
		this.novaSenha = novaSenha;
	}
	
	
}
