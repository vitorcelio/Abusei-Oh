package br.com.abusei.Abusei.repositorys;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import br.com.abusei.Abusei.models.Comentario;
import br.com.abusei.Abusei.models.Produto;

@Repository
public interface ComentarioRepository extends PagingAndSortingRepository<Comentario, Long>{
	
	public List<Comentario> findByProduto(Produto produto);
	
	@Query("SELECT c FROM Comentario c WHERE c.id = :id")
	public Comentario findId(Long id);
	
	@Query("SELECT c FROM Comentario c WHERE c.produto.user.username = :username")
	public Page<Comentario> comentariosDosAnunciosDeCadaUser(String username, Pageable pageable);
	
}
