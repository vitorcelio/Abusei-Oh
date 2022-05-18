package br.com.abusei.Abusei.dto.comentario;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import br.com.abusei.Abusei.models.Comentario;

public class RequisicaoNovoComentario {
	
	@NotBlank
	private String comentario;
	private Long idProduto;
	
	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public Long getIdProduto() {
		return idProduto;
	}

	public void setIdProduto(Long idProduto) {
		this.idProduto = idProduto;
	}

	public Comentario toComentario() {
		Comentario comen = new Comentario();
		
		comen.setComentario(comentario);
		comen.setData(LocalDateTime.now());
		
		return comen;
	}
}
