package br.com.abusei.Abusei.repositorys;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import br.com.abusei.Abusei.models.Estado;

@Repository
public interface EstadoRepository extends PagingAndSortingRepository<Estado, Long>{
	
	@Cacheable("estado")
	public List<Estado> findAll();

}
