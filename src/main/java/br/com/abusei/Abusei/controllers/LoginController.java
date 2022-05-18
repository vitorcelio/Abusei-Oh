package br.com.abusei.Abusei.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
	
	@GetMapping("/entrar")
	public String PaginaLoginUser() {
		return "entrar";
	}
}
