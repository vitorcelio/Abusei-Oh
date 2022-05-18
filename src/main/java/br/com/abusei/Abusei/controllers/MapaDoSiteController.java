package br.com.abusei.Abusei.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.abusei.Abusei.models.Categoria;
import br.com.abusei.Abusei.models.User;
import br.com.abusei.Abusei.repositorys.CategoriaRepository;
import br.com.abusei.Abusei.repositorys.UserRepository;

@Controller
@RequestMapping("mapa-do-site")
public class MapaDoSiteController {

	@Autowired
	private CategoriaRepository categoriaRepository;
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping
	public String mapa(Model model) {
		List<Categoria> categorias = categoriaRepository.findAll();
		model.addAttribute("categorias", categorias);
		
		User user = userRepository.usernameAndFotoPerfil(SecurityContextHolder.getContext().getAuthentication().getName());
		model.addAttribute("perfil", user);
		return "mapaDoSite";
	}

}