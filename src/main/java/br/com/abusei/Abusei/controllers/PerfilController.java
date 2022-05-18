package br.com.abusei.Abusei.controllers;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.abusei.Abusei.models.Categoria;
import br.com.abusei.Abusei.models.Condicao;
import br.com.abusei.Abusei.models.Contato;
import br.com.abusei.Abusei.models.Estado;
import br.com.abusei.Abusei.models.Favorito;
import br.com.abusei.Abusei.models.Produto;
import br.com.abusei.Abusei.models.StatusProduto;
import br.com.abusei.Abusei.models.User;
import br.com.abusei.Abusei.repositorys.CategoriaRepository;
import br.com.abusei.Abusei.repositorys.ContatoRepository;
import br.com.abusei.Abusei.repositorys.EstadoRepository;
import br.com.abusei.Abusei.repositorys.FavoritoRepository;
import br.com.abusei.Abusei.repositorys.ProdutoRepository;
import br.com.abusei.Abusei.repositorys.UserRepository;

@Controller
@RequestMapping("@{usuario}")
public class PerfilController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ContatoRepository contatoRepository;

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private FavoritoRepository favoritoRepository;

	@Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	private EstadoRepository estadoRepository;

	@GetMapping
	public String perfil(@PathVariable("usuario") String usuario, @RequestParam(defaultValue = "1") int page,
			Model model) {

		User user = userRepository.findByUsername(usuario);
		model.addAttribute("perfil", user);

		List<Categoria> categorias = categoriaRepository.findAll();
		model.addAttribute("categorias", categorias);

		List<Estado> estados = estadoRepository.findAll();
		model.addAttribute("estados", estados);

		Pageable pageable = PageRequest.of(page - 1, 28, Sort.by(Sort.Direction.DESC, "id"));
		Page<Produto> produtos = produtoRepository.findByUserUsernameAndStatus(usuario, StatusProduto.APROVADO,
				pageable);
		model.addAttribute("produtos", produtos);

		List<Favorito> favoritos = favoritoRepository
				.findByUserUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		model.addAttribute("favoritos", favoritos);

		Contato contato = contatoRepository.findByUserUsername(usuario);

		if (contato != null) {
			if (contato.getEmail() == null || contato.getEmail().isEmpty()) {
				model.addAttribute("email", 0);
			}
			if (contato.getFacebook() == null || contato.getFacebook().isEmpty()) {
				model.addAttribute("facebook", 0);
			}
			if (contato.getInstagram() == null || contato.getInstagram().isEmpty()) {
				model.addAttribute("instagram", 0);
			}
			if (contato.getWhatsapp() == null || contato.getWhatsapp().isEmpty()) {
				model.addAttribute("whatsapp", 0);
			}

		}

		if (user == null) {
			return "redirect:/produtos";
		}

		model.addAttribute("contato", contato);
		return "perfil";
	}

	@GetMapping("buscar")
	public String pesquisaProduto(@PathVariable("usuario") String usuario, @RequestParam(defaultValue = "1") int page,
			@RequestParam(required = false, name = "nome") String nome,
			@RequestParam(defaultValue = "0") String menorPreco,
			@RequestParam(defaultValue = "1000000") String maiorPreco,
			@RequestParam(required = false, name = "condicao") String condicao,
			@RequestParam(required = false, name = "subcategoria") String subcategoria,
			@RequestParam(required = false, name = "cidade") String cidade, Model model) {

		User user = userRepository.findByUsername(usuario);
		model.addAttribute("perfil", user);

		Pageable pageable = PageRequest.of((page - 1), 28, Sort.by(Sort.Direction.DESC, "id"));
		Page<Produto> produtos;

		List<Categoria> categorias = categoriaRepository.findAll();
		model.addAttribute("categorias", categorias);

		List<Estado> estados = estadoRepository.findAll();
		model.addAttribute("estados", estados);

		if (condicao.isEmpty() && subcategoria.isEmpty() && cidade.isEmpty()) {

			produtos = produtoRepository
					.findByStatusAndNomeContainingAndPrecoVistaGreaterThanEqualAndPrecoVistaLessThanEqualAndUserUsername(
							StatusProduto.APROVADO, nome, new BigDecimal(menorPreco), new BigDecimal(maiorPreco),
							user.getUsername(), pageable);

		} else if ((!condicao.isEmpty()) && (subcategoria.isEmpty() && cidade.isEmpty())) {

			produtos = produtoRepository
					.findByStatusAndNomeContainingAndPrecoVistaGreaterThanEqualAndPrecoVistaLessThanEqualAndCondicaoAndUserUsername(
							StatusProduto.APROVADO, nome, new BigDecimal(menorPreco), new BigDecimal(maiorPreco),
							Condicao.valueOf(condicao), user.getUsername(), pageable);

		} else if ((!subcategoria.isEmpty()) && (condicao.isEmpty() && cidade.isEmpty())) {

			produtos = produtoRepository
					.findByStatusAndNomeContainingAndPrecoVistaGreaterThanEqualAndPrecoVistaLessThanEqualAndSubcategoriaSubcategoriaAndUserUsername(
							StatusProduto.APROVADO, nome, new BigDecimal(menorPreco), new BigDecimal(maiorPreco),
							subcategoria, user.getUsername(), pageable);

		} else if ((!cidade.isEmpty()) && (subcategoria.isEmpty() && condicao.isEmpty())) {

			produtos = produtoRepository
					.findByStatusAndNomeContainingAndPrecoVistaGreaterThanEqualAndPrecoVistaLessThanEqualAndCidadeCidadeAndUserUsername(
							StatusProduto.APROVADO, nome, new BigDecimal(menorPreco), new BigDecimal(maiorPreco),
							cidade, user.getUsername(), pageable);

		} else if ((condicao.isEmpty()) && (!subcategoria.isEmpty() && !cidade.isEmpty())) {

			produtos = produtoRepository
					.findByStatusAndNomeContainingAndPrecoVistaGreaterThanEqualAndPrecoVistaLessThanEqualAndSubcategoriaSubcategoriaAndCidadeCidadeAndUserUsername(
							StatusProduto.APROVADO, nome, new BigDecimal(menorPreco), new BigDecimal(maiorPreco),
							subcategoria, cidade, user.getUsername(), pageable);

		} else if ((subcategoria.isEmpty()) && (!condicao.isEmpty() && !cidade.isEmpty())) {

			produtos = produtoRepository
					.findByStatusAndNomeContainingAndPrecoVistaGreaterThanEqualAndPrecoVistaLessThanEqualAndCondicaoAndCidadeCidadeAndUserUsername(
							StatusProduto.APROVADO, nome, new BigDecimal(menorPreco), new BigDecimal(maiorPreco),
							Condicao.valueOf(condicao), cidade, user.getUsername(), pageable);

		} else if ((cidade.isEmpty()) && (!subcategoria.isEmpty() && !condicao.isEmpty())) {

			produtos = produtoRepository
					.findByStatusAndNomeContainingAndPrecoVistaGreaterThanEqualAndPrecoVistaLessThanEqualAndSubcategoriaSubcategoriaAndCondicaoAndUserUsername(
							StatusProduto.APROVADO, nome, new BigDecimal(menorPreco), new BigDecimal(maiorPreco),
							subcategoria, Condicao.valueOf(condicao), user.getUsername(), pageable);

		} else {

			produtos = produtoRepository
					.findByStatusAndNomeContainingAndPrecoVistaGreaterThanEqualAndPrecoVistaLessThanEqualAndCondicaoAndSubcategoriaSubcategoriaAndCidadeCidadeAndUserUsername(
							StatusProduto.APROVADO, nome, new BigDecimal(menorPreco), new BigDecimal(maiorPreco),
							Condicao.valueOf(condicao), subcategoria, cidade, user.getUsername(), pageable);

		}

		Contato contato = contatoRepository.findByUserUsername(usuario);

		if (contato != null) {
			if (contato.getEmail() == null || contato.getEmail().isEmpty()) {
				model.addAttribute("email", 0);
			}
			if (contato.getFacebook() == null || contato.getFacebook().isEmpty()) {
				model.addAttribute("facebook", 0);
			}
			if (contato.getInstagram() == null || contato.getInstagram().isEmpty()) {
				model.addAttribute("instagram", 0);
			}
			if (contato.getWhatsapp() == null || contato.getWhatsapp().isEmpty()) {
				model.addAttribute("whatsapp", 0);
			}

		}


		model.addAttribute("contato", contato);

		model.addAttribute("produtos", produtos);

		model.addAttribute("tamanho", produtos.getTotalElements());

		return "perfil";
	}

	@ExceptionHandler(NullPointerException.class)
	public String erro() {
		return "redirect:/produtos";
	}

}
