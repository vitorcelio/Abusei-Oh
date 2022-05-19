package br.com.abusei.Abusei.controllers;

import java.math.BigDecimal;
import java.util.Arrays;
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

import br.com.abusei.Abusei.dto.comentario.RequisicaoNovoComentario;
import br.com.abusei.Abusei.models.Categoria;
import br.com.abusei.Abusei.models.Comentario;
import br.com.abusei.Abusei.models.Condicao;
import br.com.abusei.Abusei.models.Estado;
import br.com.abusei.Abusei.models.Favorito;
import br.com.abusei.Abusei.models.Imagem;
import br.com.abusei.Abusei.models.Produto;
import br.com.abusei.Abusei.models.StatusProduto;
import br.com.abusei.Abusei.models.User;
import br.com.abusei.Abusei.repositorys.CategoriaRepository;
import br.com.abusei.Abusei.repositorys.ComentarioRepository;
import br.com.abusei.Abusei.repositorys.EstadoRepository;
import br.com.abusei.Abusei.repositorys.FavoritoRepository;
import br.com.abusei.Abusei.repositorys.ImagemRepository;
import br.com.abusei.Abusei.repositorys.ProdutoRepository;
import br.com.abusei.Abusei.repositorys.UserRepository;

@Controller
@RequestMapping("produtos")
public class ProdutoController {

	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private CategoriaRepository categoriaRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ComentarioRepository comentarioRepository;
	@Autowired
	private FavoritoRepository favoritoRepository;
	@Autowired
	private EstadoRepository estadoRepository;
	@Autowired
	private ImagemRepository imagemRepository;

	@GetMapping
	public String listar(@RequestParam(defaultValue = "1") int page, Model model) {
		Pageable pageable = PageRequest.of((page - 1), 28, Sort.by(Sort.Direction.DESC, "id"));
		Page<Produto> produtos = produtoRepository.findByStatus(StatusProduto.APROVADO, pageable);
		model.addAttribute("produtos", produtos);

		model.addAttribute("tamanho", produtos.getTotalElements());

		List<Categoria> categorias = categoriaRepository.findAll();
		model.addAttribute("categorias", categorias);

		List<Estado> estados = estadoRepository.findAll();
		model.addAttribute("estados", estados);

		User usermenu = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		model.addAttribute("perfil", usermenu);

		return "listagem-de-produtos";
	}

	@GetMapping("{link}") // Mostra produtos na página de produtos
	public String produto(@PathVariable("link") String link, RequisicaoNovoComentario requisicao, Model model) {
		Produto produto = produtoRepository.findByLink(link);
		model.addAttribute("produto", produto);
		
		List<Imagem> imagens = imagemRepository.findByProduto(produto);
		model.addAttribute("imagens", imagens);
		
		imagens.forEach(img -> {
			if(Arrays.equals(img.getImagem(), produto.getImagem())) {
				model.addAttribute("idImagemPrincipal", img.getId());
			}
		});

		List<Comentario> comentarios = comentarioRepository.findByProduto(produto);
		model.addAttribute("comentarios", comentarios);

		List<Favorito> favoritos = favoritoRepository
				.findByUserUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		favoritos.forEach(f -> {
			if (f.getProduto().getId() == produto.getId()) {
				model.addAttribute("favorito", true);
			}
		});

		model.addAttribute("user", produto.getUser());

		User usermenu = userRepository
				.usernameAndFotoPerfil(SecurityContextHolder.getContext().getAuthentication().getName());
		model.addAttribute("perfil", usermenu);

		if (!produto.getStatus().toString().equals("APROVADO")) {
			return "redirect:/produtos";
		}

		return "produto";
	}

	@GetMapping("buscar")
	public String pesquisaProduto(
			@RequestParam(defaultValue="1") int page, 
			@RequestParam(required = false,name="nome") String nome, 
			@RequestParam(defaultValue = "0") String menorPreco, 
			@RequestParam(defaultValue = "10000000000") String maiorPreco,  
			@RequestParam(required = false,name="condicao") String condicao, 
			@RequestParam(required = false, name = "subcategoria") String subcategoria, 
			@RequestParam(required = false, name = "cidade") String cidade, 
			Model model) {
		
		
		Pageable pageable = PageRequest.of((page - 1), 28, Sort.by(Sort.Direction.DESC, "id"));
		Page<Produto> produtos;
		
		List<Categoria> categorias = categoriaRepository.findAll();
		model.addAttribute("categorias", categorias);
		
		List<Estado> estados = estadoRepository.findAll();
		model.addAttribute("estados", estados);
		
		if(condicao.isEmpty() && subcategoria.isEmpty() && cidade.isEmpty()) {
			
			produtos = produtoRepository
					.findByStatusAndNomeContainingAndPrecoVistaGreaterThanEqualAndPrecoVistaLessThanEqual(
							StatusProduto.APROVADO, 
							nome, 
							new BigDecimal(menorPreco), 
							new BigDecimal(maiorPreco), 
							pageable);
			
		} else if((!condicao.isEmpty()) && (subcategoria.isEmpty() && cidade.isEmpty())) {
			
			produtos = produtoRepository
					.findByStatusAndNomeContainingAndPrecoVistaGreaterThanEqualAndPrecoVistaLessThanEqualAndCondicao(
							StatusProduto.APROVADO, 
							nome, 
							new BigDecimal(menorPreco), 
							new BigDecimal(maiorPreco), 
							Condicao.valueOf(condicao), 
							pageable);
					
			
		} else if((!subcategoria.isEmpty()) && (condicao.isEmpty() && cidade.isEmpty())){
			
			produtos = produtoRepository
					.findByStatusAndNomeContainingAndPrecoVistaGreaterThanEqualAndPrecoVistaLessThanEqualAndSubcategoriaSubcategoria(
							StatusProduto.APROVADO, 
							nome,
							new BigDecimal(menorPreco), 
							new BigDecimal(maiorPreco), 
							subcategoria, 
							pageable);
					
			
		} else if((!cidade.isEmpty()) && (subcategoria.isEmpty() && condicao.isEmpty())){
			
			produtos = produtoRepository
					.findByStatusAndNomeContainingAndPrecoVistaGreaterThanEqualAndPrecoVistaLessThanEqualAndCidadeCidade(
							StatusProduto.APROVADO, 
							nome, 
							new BigDecimal(menorPreco), 
							new BigDecimal(maiorPreco),
							cidade, 
							pageable);
			
		}else if((condicao.isEmpty()) && (!subcategoria.isEmpty() && !cidade.isEmpty())) {
			
			produtos = produtoRepository
					.findByStatusAndNomeContainingAndPrecoVistaGreaterThanEqualAndPrecoVistaLessThanEqualAndSubcategoriaSubcategoriaAndCidadeCidade(
							StatusProduto.APROVADO, 
							nome, 
							new BigDecimal(menorPreco), 
							new BigDecimal(maiorPreco), 
							subcategoria, 
							cidade, 
							pageable);
			
		}else if((subcategoria.isEmpty()) && (!condicao.isEmpty() && !cidade.isEmpty())) {
			
			produtos = produtoRepository
					.findByStatusAndNomeContainingAndPrecoVistaGreaterThanEqualAndPrecoVistaLessThanEqualAndCondicaoAndCidadeCidade(
							StatusProduto.APROVADO, 
							nome, 
							new BigDecimal(menorPreco), 
							new BigDecimal(maiorPreco), 
							Condicao.valueOf(condicao), 
							cidade, 
							pageable);
			
		}else if((cidade.isEmpty()) && (!subcategoria.isEmpty() && !condicao.isEmpty() )) {
			
			produtos = produtoRepository
					.findByStatusAndNomeContainingAndPrecoVistaGreaterThanEqualAndPrecoVistaLessThanEqualAndSubcategoriaSubcategoriaAndCondicao(
							StatusProduto.APROVADO, 
							nome, 
							new BigDecimal(menorPreco), 
							new BigDecimal(maiorPreco), 
							subcategoria, 
							Condicao.valueOf(condicao), 
							pageable);
			
		}else {
		
			
			produtos = produtoRepository
					.findByStatusAndNomeContainingAndPrecoVistaGreaterThanEqualAndPrecoVistaLessThanEqualAndCondicaoAndSubcategoriaSubcategoriaAndCidadeCidade(
							StatusProduto.APROVADO, 
							nome, 
							new BigDecimal(menorPreco), 
							new BigDecimal(maiorPreco), 
							Condicao.valueOf(condicao), 
							subcategoria, 
							cidade,
							pageable);
			
		}
		
		
		
		model.addAttribute("produtos", produtos);

		model.addAttribute("tamanho", produtos.getTotalElements());

		User usermenu = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		model.addAttribute("perfil", usermenu);
		return "listagem-de-produtos";
	}

	@ExceptionHandler(NullPointerException.class)
	public String erro() {
		return "redirect:/produtos";
	}

	@GetMapping("categoria/{categoria}")
	public String produtosCategorias(@RequestParam(defaultValue = "1") int page,
			@PathVariable("categoria") String categoria, Model model) {
		Pageable pageable = PageRequest.of((page - 1), 28, Sort.by(Sort.Direction.DESC, "id"));
		Page<Produto> produtos = produtoRepository.findByStatusAndSubcategoriaCategoriaCategoria(StatusProduto.APROVADO,
				categoria, pageable);
		model.addAttribute("produtos", produtos);

		model.addAttribute("tamanho", produtos.getTotalElements());

		User usermenu = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		model.addAttribute("perfil", usermenu);

		return "listagem-de-produtos";
	}

	@GetMapping("subcategoria/{subcategoria}")
	public String produtosSubategorias(@RequestParam(defaultValue = "1") int page,
			@PathVariable("subcategoria") String subcategoria, Model model) {
		Pageable pageable = PageRequest.of((page - 1), 28, Sort.by(Sort.Direction.DESC, "id"));
		Page<Produto> produtos = produtoRepository.findByStatusAndSubcategoriaSubcategoria(StatusProduto.APROVADO,
				subcategoria, pageable);
		model.addAttribute("produtos", produtos);

		model.addAttribute("tamanho", produtos.getTotalElements());

		User usermenu = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		model.addAttribute("perfil", usermenu);

		return "listagem-de-produtos";
	}
}
