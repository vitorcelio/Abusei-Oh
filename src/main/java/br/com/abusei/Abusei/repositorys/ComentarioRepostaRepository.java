package br.com.abusei.Abusei.repositorys;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import br.com.abusei.Abusei.models.ComentarioReposta;

@Repository
public interface ComentarioRepostaRepository extends PagingAndSortingRepository<ComentarioReposta, Long>{

}
