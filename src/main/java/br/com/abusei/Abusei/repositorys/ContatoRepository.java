package br.com.abusei.Abusei.repositorys;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import br.com.abusei.Abusei.models.Contato;

@Repository
public interface ContatoRepository extends PagingAndSortingRepository<Contato, Long>{
	
	@Cacheable("contato")
	public Contato findByUserUsername(String username);
	
}
