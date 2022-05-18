package br.com.abusei.Abusei.repositorys;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import br.com.abusei.Abusei.models.Subcategoria;

@Repository
public interface SubcategoriaRepository extends PagingAndSortingRepository<Subcategoria, Long>{
	
	@Query("SELECT s FROM Subcategoria s where s.id = :id")
	public Subcategoria findId(Long id);
}
