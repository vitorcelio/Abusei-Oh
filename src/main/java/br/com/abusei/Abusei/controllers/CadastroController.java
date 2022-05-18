package br.com.abusei.Abusei.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import br.com.abusei.Abusei.dto.user.RequisicaoCadastrarUsuario;
import br.com.abusei.Abusei.models.Authorities;
import br.com.abusei.Abusei.models.User;
import br.com.abusei.Abusei.repositorys.AuthoritiesRepository;
import br.com.abusei.Abusei.repositorys.UserRepository;

@Controller
public class CadastroController{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AuthoritiesRepository authoritiesRepository;

	@GetMapping("/cadastrar")
	public String PaginaCadastroUser(RequisicaoCadastrarUsuario requisicao) {
		return "cadastrar";
	}

	@PostMapping("/cadastrar")
	public String cadastrar(@Valid RequisicaoCadastrarUsuario requisicao, BindingResult result, Model model) {
		
		User userVerifica = userRepository.findByUsername(requisicao.getUsername());
		
		if(result.hasErrors()) {
			if(userVerifica != null) model.addAttribute("erro", "Este nome de usuário não está disponível!") ;
			return "cadastrar";
		}
		
		if(userVerifica != null) {
			model.addAttribute("erro", "Este nome de usuário não está disponível!") ;
			return "cadastrar";
		}
		
		User user = requisicao.toUser();
		userRepository.save(user);
		
		Authorities authorities = new Authorities(user.getUsername(), user.getRoles());
		authoritiesRepository.save(authorities);
		
		return "redirect:/dashboard/relatorio";
	}
	
	
	


}
