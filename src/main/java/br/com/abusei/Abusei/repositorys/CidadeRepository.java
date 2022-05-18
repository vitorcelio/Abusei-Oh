package br.com.abusei.Abusei.repositorys;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import br.com.abusei.Abusei.models.Cidade;

@Repository
public interface CidadeRepository extends PagingAndSortingRepository<Cidade, Long>{
	
	@Cacheable("cidade")
	public Cidade findByCidade(String cidade);

}
