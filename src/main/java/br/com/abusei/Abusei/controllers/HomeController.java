package br.com.abusei.Abusei.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.abusei.Abusei.models.Categoria;
import br.com.abusei.Abusei.models.Produto;
import br.com.abusei.Abusei.models.StatusProduto;
import br.com.abusei.Abusei.models.User;
import br.com.abusei.Abusei.repositorys.CategoriaRepository;
import br.com.abusei.Abusei.repositorys.ProdutoRepository;
import br.com.abusei.Abusei.repositorys.UserRepository;

@Controller
@RequestMapping(path = {"inicio", ""})
public class HomeController {
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@GetMapping
	public String home(Model model) {
		Pageable pageable = PageRequest.of(0, 24, Sort.by(Sort.Direction.DESC, "id"));
		Page<Produto> produtos = produtoRepository.findByStatus(StatusProduto.APROVADO, pageable);
		model.addAttribute("produtos", produtos);
		model.addAttribute("elementos", produtos.getTotalElements());
		
		Pageable pageable2 = PageRequest.of(0, 11, Sort.by(Sort.Direction.DESC, "id"));
		Page<Categoria> categorias = categoriaRepository.findAll(pageable2);
		model.addAttribute("categorias", categorias);
		
		User usermenu = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		model.addAttribute("perfil", usermenu);
		return "home";
	}
	
}
