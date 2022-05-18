package br.com.abusei.Abusei.dto.contato;

import javax.validation.constraints.NotBlank;

public class RequisicaoAtualizarContato {
	
	private String emailContato;
	
	@NotBlank(message = "O Facebook precisa estar preenchido!")
	private String facebook;
	
	private String instagram;
	
	@NotBlank(message = "O Whatsapp precisa estar preenchido!")
	private String whatsapp;
	
	
	public String getEmailContato() {
		return emailContato;
	}
	public void setEmailContato(String emailContato) {
		this.emailContato = emailContato;
	}
	public String getFacebook() {
		return facebook;
	}
	public void setFacebook(String facebook) {
		this.facebook = facebook;
	}
	public String getInstagram() {
		return instagram;
	}
	public void setInstagram(String instagram) {
		this.instagram = instagram;
	}
	public String getWhatsapp() {
		return whatsapp;
	}
	public void setWhatsapp(String whatsapp) {
		this.whatsapp = whatsapp;
	}
	
	
}
