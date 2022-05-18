package br.com.abusei.Abusei.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.abusei.Abusei.models.Authorities;

public interface AuthoritiesRepository extends JpaRepository<Authorities, String>{
	
}
