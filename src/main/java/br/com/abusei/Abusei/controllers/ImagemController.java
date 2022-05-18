package br.com.abusei.Abusei.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.abusei.Abusei.models.User;
import br.com.abusei.Abusei.repositorys.UserRepository;

@Controller
@RequestMapping("imagem")
public class ImagemController {

	@Autowired
	private UserRepository userRepository;
	
	private static String URL_PERFIL = "src/main/resources/static/uploads/img-perfil";
	private static String URL_CAPA = "src/main/resources/static/uploads/img-capa";
	private static String URL_PRODUTOS = "src/main/resources/static/uploads/img-produtos";
	
	@GetMapping("mostrarPerfil/{imagem}")
	@ResponseBody
	public byte[] visualizarPerfil(@PathVariable("imagem") String imagem) {
		File imagemArquivo = new File(URL_PERFIL + File.separator + imagem);
		try {
			return Files.readAllBytes(imagemArquivo.toPath());
		} catch (IOException e) {
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
	
	@GetMapping("mostrarCapa/{imagem}")
	@ResponseBody
	public byte[] visualizarCapa(@PathVariable("imagem") String imagem) {
		File imagemArquivo = new File(URL_CAPA + File.separator + imagem);
		try {
			return Files.readAllBytes(imagemArquivo.toPath());
		} catch (IOException e) {
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
	
	@GetMapping("mostrarProdutos/{imagem}")
	public @ResponseBody byte[] visualizarProdutos(@PathVariable("imagem") String imagem) {
		File imagemArquivo = new File(URL_PRODUTOS + File.separator + imagem);
		try {
			return Files.readAllBytes(imagemArquivo.toPath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}