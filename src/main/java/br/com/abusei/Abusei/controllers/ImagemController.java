package br.com.abusei.Abusei.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.abusei.Abusei.models.Imagem;
import br.com.abusei.Abusei.models.Produto;
import br.com.abusei.Abusei.models.User;
import br.com.abusei.Abusei.repositorys.ImagemRepository;
import br.com.abusei.Abusei.repositorys.ProdutoRepository;
import br.com.abusei.Abusei.repositorys.UserRepository;

@Controller
@RequestMapping("imagem")
public class ImagemController {

	@Autowired
	private ImagemRepository imagemRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@GetMapping("mostrarProduto/{idProduto}")
	@ResponseBody
	public byte[] visualizarProduto(@PathVariable("idProduto") Long id) {
		Produto produto = produtoRepository.findById(id).get();
		
		try {
			return produto.getImagem();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@GetMapping("mostrarPerfil/{username}")
	@ResponseBody
	public byte[] visualizarPerfil(@PathVariable("username") String username) {
		User user = userRepository.findByUsername(username);
		
		try {
			return user.getFotoPerfil();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@GetMapping("removerPerfil")
	public String RemoverPerfil(Principal principal) {
		
		User user = userRepository.findByUsername(principal.getName());
		user.setFotoPerfil(null);
		userRepository.save(user);
		
		return "redirect:/dashboard/configuracoes";
	}
	
	@GetMapping("mostrarCapa/{username}")
	@ResponseBody
	public byte[] visualizarCapa(@PathVariable("username") String username) {
		User user = userRepository.findByUsername(username);
		
		try {
			return user.getFotoCapa();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@GetMapping("removerCapa")
	public String RemoverCapa(Principal principal) {
		
		User user = userRepository.findByUsername(principal.getName());
		user.setFotoCapa(null);
		userRepository.save(user);
		
		return "redirect:/dashboard/configuracoes";
	}
	
	@GetMapping("mostrarProdutos/{id}")
	@ResponseBody
	public  byte[] visualizarProdutos(@PathVariable("id") Long id) {
		Imagem imagem = imagemRepository.findById(id).get();
		
		try {
			return imagem.getImagem();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}