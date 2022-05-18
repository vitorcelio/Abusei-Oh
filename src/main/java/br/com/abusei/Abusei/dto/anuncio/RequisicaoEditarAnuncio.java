package br.com.abusei.Abusei.dto.anuncio;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

public class RequisicaoEditarAnuncio {
	
	private Long idProduto;
	
	@NotBlank(message = "O nome deve ser preenchido!")
	private String nome;
	
	@NotBlank(message = "Selecione uma condição para seu produto!")
	private String condicao;
	
	private String status;
	
	@NotBlank(message = "Seu produto não pode ficar sem preço!")
	private String precoVista;
	
	private String precoCortado;
	
	private String precoCartao;
	
	@Min(value = 0, message = "Valor das parcelas têm que ser positivos!")
	@Max(value = 24, message = "Valor máximo de parcelas é 24!")
	private int parcelasCartao;
	 
	@NotNull(message = "Seu produto precisa de uma categoria!")
	private Long idCategoria;
	
	private String descricao;
	
	@NotBlank(message = "Seu produto precisa de uma localização!")
	private String cidade;
	
//	private MultipartFile[] files;
	
	private MultipartFile file;
	
	public Long getIdProduto() {
		return idProduto;
	}
	public void setIdProduto(Long idProduto) {
		this.idProduto = idProduto;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCondicao() {
		return condicao;
	}
	public void setCondicao(String condicao) {
		this.condicao = condicao;
	}
	public String getPrecoVista() {
		return precoVista;
	}
	public void setPrecoVista(String precoVista) {
		this.precoVista = precoVista;
	}
	public String getPrecoCortado() {
		return precoCortado;
	}
	public void setPrecoCortado(String precoCortado) {
		this.precoCortado = precoCortado;
	}
	public String getPrecoCartao() {
		return precoCartao;
	}
	public void setPrecoCartao(String precoCartao) {
		this.precoCartao = precoCartao;
	}
	public int getParcelasCartao() {
		return parcelasCartao;
	}
	public void setParcelasCartao(int parcelasCartao) {
		this.parcelasCartao = parcelasCartao;
	}
	public Long getIdCategoria() {
		return idCategoria;
	}
	public void setIdCategoria(Long idCategoria) {
		this.idCategoria = idCategoria;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
//	public MultipartFile[] getFiles() {
//		return files;
//	}
//	public void setFiles(MultipartFile[] file) {
//		this.files = file;
//	}
	
	
	
	public String getCidade() {
		return cidade;
	}
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
}
