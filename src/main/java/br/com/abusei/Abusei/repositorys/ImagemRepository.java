package br.com.abusei.Abusei.repositorys;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.abusei.Abusei.models.Imagem;
import br.com.abusei.Abusei.models.Produto;

public interface ImagemRepository extends JpaRepository<Imagem, Long>{
	public Imagem findByProdutoId(Long id);
	
	public List<Imagem> findByProduto(Produto produto);
	
	@Query("SELECT i FROM Imagem i where i.id = :id")
	public Imagem findId(Long id);
}
