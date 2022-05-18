package br.com.abusei.Abusei.dto.comentario;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import br.com.abusei.Abusei.models.ComentarioReposta;

public class RequisicaoRespostaComentario {
	
	@NotBlank
	private String resposta;
	private Long idComentario;
	
	public String getResposta() {
		return resposta;
	}
	public void setResposta(String resposta) {
		this.resposta = resposta;
	}
	public Long getIdComentario() {
		return idComentario;
	}
	public void setIdComentario(Long idComentario) {
		this.idComentario = idComentario;
	}
	
	public ComentarioReposta toComentarioReposta() {
		ComentarioReposta com = new ComentarioReposta();
		
		com.setComentarioReposta(resposta);
		com.setData(LocalDateTime.now());
		
		return com;
	}
}
