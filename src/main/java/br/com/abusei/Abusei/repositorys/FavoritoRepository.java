package br.com.abusei.Abusei.repositorys;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import br.com.abusei.Abusei.models.Favorito;
import br.com.abusei.Abusei.models.Produto;

@Repository
public interface FavoritoRepository extends PagingAndSortingRepository<Favorito, Long>{
	public Page<Favorito> findByUserUsername(String username, Pageable pageable);
	
	public Favorito findByProdutoAndUserUsername(Produto produto, String username);
	
	public List<Favorito> findByUserUsername(String username);
	
}
