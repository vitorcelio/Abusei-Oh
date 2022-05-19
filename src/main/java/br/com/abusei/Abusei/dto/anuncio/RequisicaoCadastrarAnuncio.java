package br.com.abusei.Abusei.dto.anuncio;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import br.com.abusei.Abusei.models.Condicao;
import br.com.abusei.Abusei.models.Produto;
import br.com.abusei.Abusei.models.StatusProduto;

public class RequisicaoCadastrarAnuncio {

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

//	LOCAL
	private MultipartFile[] files;
	
//	PRODUÇÃO
//	private MultipartFile file;
	
	
	private String linkAleatorio = UUID.randomUUID().toString().replace("-", "");

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

	public MultipartFile[] getFiles() {
		return files;
	}

	public void setFiles(MultipartFile[] file) {
		this.files = file;
	}
	

//	public MultipartFile getFile() {
//		return file;
//	}
//	
//	public void setFile(MultipartFile file) {
//		this.file = file;
//	}
	public String getCidade() {
		return cidade;
	}


	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getLinkAleatorio() {
		return linkAleatorio;
	}

	public void setLinkAleatorio(String linkAleatorio) {
		this.linkAleatorio = linkAleatorio;
	}

	public Produto toProduto() throws IOException {

		BigDecimal b1 = null;
		if (precoCortado != null && !precoCortado.isEmpty()) {
			b1 = new BigDecimal(precoCortado);
		}

		BigDecimal b3 = null;
		if (precoCartao != null && !precoCartao.isEmpty()) {
			b3 = new BigDecimal(precoCartao);
		}

		BigDecimal b2 = new BigDecimal(precoVista);

		Produto produto = new Produto();

		produto.getNumeros().add(linkAleatorio);
		produto.setNome(nome);
		produto.setCondicao(Condicao.valueOf(condicao));
		produto.setStatus(StatusProduto.PENDENTE);
		produto.setDescricao(descricao);
		produto.setPrecoVista(new BigDecimal(precoVista));
		produto.setParcelasCartao(1);
		
		if(files.length > 0) {
			produto.setImagem(files[0].getBytes());
		} else {
			produto.setImagem(null);
		}

		if (b1 != null) {
			if (b1.compareTo(b2) == 1) {
				produto.setPrecoCortado(new BigDecimal(precoCortado));
			}
		}

		if (b3 != null) {
			if (b2.compareTo(b3) == 2 || b2.compareTo(b3) == 0) {
				produto.setPrecoCartao(new BigDecimal(precoCartao));
				produto.setParcelasCartao(parcelasCartao);
			}
		}

		produto.setData(LocalDateTime.now());
		produto.setLink(linkAleatorio);

		return produto;

	}

}
