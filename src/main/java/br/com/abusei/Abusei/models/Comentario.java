package br.com.abusei.Abusei.models;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "comentarios")
public class Comentario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String comentario;
	
	@ManyToOne
	private Produto produto;
	
	@ManyToOne
	private User user;
	
	@OneToMany(mappedBy = "comentario" , fetch = FetchType.EAGER)
	private List<ComentarioReposta> respostas;
	
	private LocalDateTime data;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<ComentarioReposta> getResposta() {
		return respostas;
	}

	public void setResposta(List<ComentarioReposta> resposta) {
		this.respostas = resposta;
	}

	public LocalDateTime getData() {
		return data;
	}

	public void setData(LocalDateTime data) {
		this.data = data;
	}

	public List<ComentarioReposta> getRespostas() {
		return respostas;
	}

	public void setRespostas(List<ComentarioReposta> respostas) {
		this.respostas = respostas;
	}
	
	
}
