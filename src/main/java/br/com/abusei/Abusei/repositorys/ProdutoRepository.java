package br.com.abusei.Abusei.repositorys;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import br.com.abusei.Abusei.models.Condicao;
import br.com.abusei.Abusei.models.Produto;
import br.com.abusei.Abusei.models.StatusProduto;

@Repository
public interface ProdutoRepository extends PagingAndSortingRepository<Produto, Long>{
	
	public Page<Produto> findByStatus(StatusProduto status, Pageable pageable);
	
	public List<Produto> findByStatus(StatusProduto status);
	
	public Page<Produto> findByUserUsername(String username, Pageable pageable);
	
//	@Query("SELECT p FROM Produto p WHERE p.user.username = :username AND p.status = :status")
	public Page<Produto> findByUserUsernameAndStatus(String username, StatusProduto status, Pageable pageable);
	
	public List<Produto> findByUserUsernameAndStatus(String username, StatusProduto status);
	
	//CONDICAO E SUBCATEGORIA E LOCALIZACAO VAZIAS
	public Page<Produto> findByStatusAndNomeContainingAndPrecoVistaGreaterThanEqualAndPrecoVistaLessThanEqual
	(StatusProduto status, String nome, BigDecimal menorPreco, BigDecimal maiorPreco, Pageable pageable);
	//CONDICAO E SUBCATEGORIA E LOCALIZACAO PREENCHIDAS
	public Page<Produto> findByStatusAndNomeContainingAndPrecoVistaGreaterThanEqualAndPrecoVistaLessThanEqualAndCondicaoAndSubcategoriaSubcategoriaAndCidadeCidade
	(StatusProduto status, String nome, BigDecimal menorPreco, BigDecimal maiorPreco, Condicao condicao, String subcategoria, String cidade, Pageable pageable);
	//SUBCATEGORIA PREENCHIDA
	public Page<Produto> findByStatusAndNomeContainingAndPrecoVistaGreaterThanEqualAndPrecoVistaLessThanEqualAndSubcategoriaSubcategoria
	(StatusProduto status, String nome, BigDecimal menorPreco, BigDecimal maiorPreco, String subcategoria, Pageable pageable);
	//CONDICAO PREENCHIDA
	public Page<Produto> findByStatusAndNomeContainingAndPrecoVistaGreaterThanEqualAndPrecoVistaLessThanEqualAndCondicao
	(StatusProduto status, String nome, BigDecimal menorPreco, BigDecimal maiorPreco, Condicao condicao, Pageable pageable);
	//LOCALIZACAO PREENCHIDA
	public Page<Produto> findByStatusAndNomeContainingAndPrecoVistaGreaterThanEqualAndPrecoVistaLessThanEqualAndCidadeCidade
	(StatusProduto status, String nome, BigDecimal menorPreco, BigDecimal maiorPreco, String cidade, Pageable pageable);
	//SUBCATEGORIA E CONDICAO PREENCHIDA
	public Page<Produto> findByStatusAndNomeContainingAndPrecoVistaGreaterThanEqualAndPrecoVistaLessThanEqualAndSubcategoriaSubcategoriaAndCondicao
	(StatusProduto status, String nome, BigDecimal menorPreco, BigDecimal maiorPreco, String subcategoria, Condicao condicao, Pageable pageable);
	//SUBCATEGORIA E LOCALIZACAO PREENCHIDA
	public Page<Produto> findByStatusAndNomeContainingAndPrecoVistaGreaterThanEqualAndPrecoVistaLessThanEqualAndSubcategoriaSubcategoriaAndCidadeCidade
	(StatusProduto status, String nome, BigDecimal menorPreco, BigDecimal maiorPreco, String subcategoria, String cidade, Pageable pageable);
	//CONDICAO E LOCALIZACAO PREENCHIDA
	public Page<Produto> findByStatusAndNomeContainingAndPrecoVistaGreaterThanEqualAndPrecoVistaLessThanEqualAndCondicaoAndCidadeCidade
	(StatusProduto status, String nome, BigDecimal menorPreco, BigDecimal maiorPreco, Condicao condicao, String cidade, Pageable pageable);
	
	public Produto findByLink(String link);
	
	@Query("SELECT p FROM Produto p where p.id = :id")
	public Produto findId(Long id);
	
	public Page<Produto> findByStatusAndSubcategoriaCategoriaCategoria(StatusProduto status, String categoria, Pageable pageable);
	
	public Page<Produto> findByStatusAndSubcategoriaSubcategoria(StatusProduto status, String subcategoria, Pageable pageable);
	
	//CONDICAO E SUBCATEGORIA E LOCALIZACAO VAZIAS POR USUARIO
		public Page<Produto> findByStatusAndNomeContainingAndPrecoVistaGreaterThanEqualAndPrecoVistaLessThanEqualAndUserUsername
		(StatusProduto status, String nome, BigDecimal menorPreco, BigDecimal maiorPreco, String username, Pageable pageable);
		//CONDICAO E SUBCATEGORIA E LOCALIZACAO PREENCHIDAS POR USUARIO
		public Page<Produto> findByStatusAndNomeContainingAndPrecoVistaGreaterThanEqualAndPrecoVistaLessThanEqualAndCondicaoAndSubcategoriaSubcategoriaAndCidadeCidadeAndUserUsername
		(StatusProduto status, String nome, BigDecimal menorPreco, BigDecimal maiorPreco, Condicao condicao, String subcategoria, String cidade, String username, Pageable pageable);
		//SUBCATEGORIA PREENCHIDA POR USUARIO
		public Page<Produto> findByStatusAndNomeContainingAndPrecoVistaGreaterThanEqualAndPrecoVistaLessThanEqualAndSubcategoriaSubcategoriaAndUserUsername
		(StatusProduto status, String nome, BigDecimal menorPreco, BigDecimal maiorPreco, String subcategoria, String username, Pageable pageable);
		//CONDICAO PREENCHIDA POR USUARIO
		public Page<Produto> findByStatusAndNomeContainingAndPrecoVistaGreaterThanEqualAndPrecoVistaLessThanEqualAndCondicaoAndUserUsername
		(StatusProduto status, String nome, BigDecimal menorPreco, BigDecimal maiorPreco, Condicao condicao, String username, Pageable pageable);
		//LOCALIZACAO PREENCHIDA POR USUARIO
		public Page<Produto> findByStatusAndNomeContainingAndPrecoVistaGreaterThanEqualAndPrecoVistaLessThanEqualAndCidadeCidadeAndUserUsername
		(StatusProduto status, String nome, BigDecimal menorPreco, BigDecimal maiorPreco, String cidade, String username, Pageable pageable);
		//SUBCATEGORIA E CONDICAO PREENCHIDA POR USUARIO
		public Page<Produto> findByStatusAndNomeContainingAndPrecoVistaGreaterThanEqualAndPrecoVistaLessThanEqualAndSubcategoriaSubcategoriaAndCondicaoAndUserUsername
		(StatusProduto status, String nome, BigDecimal menorPreco, BigDecimal maiorPreco, String subcategoria, Condicao condicao, String username, Pageable pageable);
		//SUBCATEGORIA E LOCALIZACAO PREENCHIDA POR USUARIO
		public Page<Produto> findByStatusAndNomeContainingAndPrecoVistaGreaterThanEqualAndPrecoVistaLessThanEqualAndSubcategoriaSubcategoriaAndCidadeCidadeAndUserUsername
		(StatusProduto status, String nome, BigDecimal menorPreco, BigDecimal maiorPreco, String subcategoria, String cidade, String username, Pageable pageable);
		//CONDICAO E LOCALIZACAO PREENCHIDA POR USUARIO
		public Page<Produto> findByStatusAndNomeContainingAndPrecoVistaGreaterThanEqualAndPrecoVistaLessThanEqualAndCondicaoAndCidadeCidadeAndUserUsername
		(StatusProduto status, String nome, BigDecimal menorPreco, BigDecimal maiorPreco, Condicao condicao, String cidade, String username, Pageable pageable);
}
