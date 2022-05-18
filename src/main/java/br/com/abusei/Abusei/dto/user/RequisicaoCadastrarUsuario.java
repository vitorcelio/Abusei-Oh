package br.com.abusei.Abusei.dto.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.abusei.Abusei.models.Contato;
import br.com.abusei.Abusei.models.User;

public class RequisicaoCadastrarUsuario {

	@NotBlank(message = "Campo obrigatório! Não deve estar em branco!")
	private String nome;

	@NotBlank(message = "Campo obrigatório! Não deve estar em branco!")
	private String sobrenome;
	
	@NotBlank(message = "Campo obrigatório! Não deve estar em branco!")
	@Pattern(regexp = "\\S+", message = "Não deve ter espaços em branco!")
	private String username;

	@Email
	@Pattern(regexp = "[a-zA-Z0-9]{0,}([.]?[a-zA-Z0-9]{1,})[@](gmail.com|hotmail.com|outlook.com|yahoo.com)", message="Seu Email deve ter \"@gmail.com\", \"@hotmail.com\", \"@outlook.com\" ou \"@yahoo.com\"")
	private String email;

	@NotBlank(message = "Campo obrigatório! Não deve estar em branco!")
	@Size(min = 8, message = "Deve ter mais de 8 caracteres!")
	private String senha;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobreome) {
		this.sobrenome = sobreome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public User toUser() {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		Contato contato = new Contato();
		User user = new User();
		
		user.setUsername(username);
		user.setEnabled(true);
		user.setNome(nome);
		user.setSobrenome(sobrenome);
		user.setEmail(email);
		user.setPassword(encoder.encode(senha));
		user.setRoles("ROLE_USER");
		user.setContato(contato);

		return user;
	}

}
