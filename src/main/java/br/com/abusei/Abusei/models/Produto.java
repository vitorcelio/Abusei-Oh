package br.com.abusei.Abusei.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "produtos")
public class Produto {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	private User user;
	
	@Enumerated(EnumType.STRING)
	private Condicao condicao;
	
	private String nome;
	private BigDecimal precoVista;
	private BigDecimal precoCortado;
	private BigDecimal precoCartao;
	private Integer parcelasCartao;
	
	@ManyToOne
	private Subcategoria subcategoria;
	
	@ManyToOne
	private Cidade cidade;
	
	@OneToMany(mappedBy = "produto", cascade = CascadeType.ALL)
	private List<Imagem> imagens = new ArrayList<>();
	
	private String descricao;
	
	@OneToMany(mappedBy = "produto", cascade = CascadeType.ALL)
	private List<Comentario> comentarios;
	
	@Enumerated(EnumType.STRING)
	private StatusProduto status;
	
	@OneToMany(mappedBy = "produto", cascade = CascadeType.ALL)
	private List<Favorito> favoritos;
	
	@Column(nullable = false, unique = true)
	private String link;
	
	private LocalDateTime data;
	
	private String imagem;
	
	@Transient
	private Set<String> numeros = new TreeSet<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Condicao getCondicao() {
		return condicao;
	}

	public void setCondicao(Condicao condicao) {
		this.condicao = condicao;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public BigDecimal getPrecoVista() {
		return precoVista;
	}

	public void setPrecoVista(BigDecimal precoVista) {
		this.precoVista = precoVista;
	}

	public BigDecimal getPrecoCortado() {
		return precoCortado;
	}

	public void setPrecoCortado(BigDecimal precoCortado) {
		this.precoCortado = precoCortado;
	}

	public BigDecimal getPrecoCartao() {
		return precoCartao;
	}

	public void setPrecoCartao(BigDecimal precoCartao) {
		this.precoCartao = precoCartao;
	}

	public Integer getParcelasCartao() {
		return parcelasCartao;
	}

	public void setParcelasCartao(Integer parcelasCartao) {
		this.parcelasCartao = parcelasCartao;
	}

	public Subcategoria getSubcategoria() {
		return subcategoria;
	}

	public void setSubcategoria(Subcategoria subcategoria) {
		this.subcategoria = subcategoria;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public List<Imagem> getImagens() {
		return imagens;
	}

	public void setImagens(List<Imagem> imagens) {
		this.imagens = imagens;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<Comentario> getComentarios() {
		return comentarios;
	}

	public void setComentarios(List<Comentario> comentarios) {
		this.comentarios = comentarios;
	}

	public StatusProduto getStatus() {
		return status;
	}

	public void setStatus(StatusProduto status) {
		this.status = status;
	}

	public List<Favorito> getFavoritos() {
		return favoritos;
	}

	public void setFavoritos(List<Favorito> favoritos) {
		this.favoritos = favoritos;
	}

	public LocalDateTime getData() {
		return data;
	}

	public void setData(LocalDateTime data) {
		this.data = data;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public Set<String> getNumeros() {
		return numeros;
	}

	public void setNumeros(Set<String> numeros) {
		this.numeros = numeros;
	}

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}
	
	
	
	
}
