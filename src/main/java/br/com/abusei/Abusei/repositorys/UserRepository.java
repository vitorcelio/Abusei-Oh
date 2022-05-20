package br.com.abusei.Abusei.repositorys;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import br.com.abusei.Abusei.models.User;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long>{
	
	public User findByUsername(String username);
	
	public User findByEmail(String email);
	
	@Query("SELECT new br.com.abusei.Abusei.models.User(user.username, user.nome, user.fotoPerfil, user.fotoCapa) from User user where user.username = :username")
	public User usernameAndFotoPerfil(String username);
	
	public List<User> findAll();
	
}
