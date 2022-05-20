package br.com.abusei.Abusei.controllers;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import br.com.abusei.Abusei.dto.anuncio.RequisicaoCadastrarAnuncio;
import br.com.abusei.Abusei.dto.anuncio.RequisicaoEditarAnuncio;
import br.com.abusei.Abusei.dto.comentario.RequisicaoNovoComentario;
import br.com.abusei.Abusei.dto.comentario.RequisicaoRespostaComentario;
import br.com.abusei.Abusei.dto.contato.RequisicaoAtualizarContato;
import br.com.abusei.Abusei.dto.privacidade.RequisicaoAtualizarPrivacidade;
import br.com.abusei.Abusei.dto.user.RequisicaoAtualizarUsuario;
import br.com.abusei.Abusei.models.Categoria;
import br.com.abusei.Abusei.models.Cidade;
import br.com.abusei.Abusei.models.Comentario;
import br.com.abusei.Abusei.models.ComentarioReposta;
import br.com.abusei.Abusei.models.Condicao;
import br.com.abusei.Abusei.models.Contato;
import br.com.abusei.Abusei.models.Estado;
import br.com.abusei.Abusei.models.Favorito;
import br.com.abusei.Abusei.models.Imagem;
import br.com.abusei.Abusei.models.Produto;
import br.com.abusei.Abusei.models.StatusProduto;
import br.com.abusei.Abusei.models.Subcategoria;
import br.com.abusei.Abusei.models.User;
import br.com.abusei.Abusei.repositorys.CategoriaRepository;
import br.com.abusei.Abusei.repositorys.CidadeRepository;
import br.com.abusei.Abusei.repositorys.ComentarioRepository;
import br.com.abusei.Abusei.repositorys.ComentarioRepostaRepository;
import br.com.abusei.Abusei.repositorys.ContatoRepository;
import br.com.abusei.Abusei.repositorys.EstadoRepository;
import br.com.abusei.Abusei.repositorys.FavoritoRepository;
import br.com.abusei.Abusei.repositorys.ImagemRepository;
import br.com.abusei.Abusei.repositorys.ProdutoRepository;
import br.com.abusei.Abusei.repositorys.SubcategoriaRepository;
import br.com.abusei.Abusei.repositorys.UserRepository;

@Controller
@RequestMapping("dashboard")
public class UserController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private FavoritoRepository favoritoRepository;
	
	@Autowired
	private ContatoRepository contatoRepository; 
	
	@Autowired
	private ImagemRepository imagemRepository;
	
	@Autowired
	private ComentarioRepository comentarioRepository;
	
	@Autowired
	private ComentarioRepostaRepository comentarioRepostaRepository;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private SubcategoriaRepository subcategoriaRepository;
	
	@Autowired 
	private EstadoRepository estadoRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	
	@GetMapping("produtos")
	public String mostrarProdutos(@RequestParam(defaultValue = "1") int page,Model model, String pagina, Principal p) {
		Pageable pageable = PageRequest.of((page - 1), 28, Sort.by(Sort.Direction.DESC, "id"));
		Page<Produto> produtos = produtoRepository.findByUserUsername(p.getName(), pageable);
		model.addAttribute("produtos", produtos);
		
		User usermenu = userRepository.usernameAndFotoPerfil(SecurityContextHolder.getContext().getAuthentication().getName());
		model.addAttribute("perfil", usermenu);
		
		pagina = "produtos";
		model.addAttribute("pagina", pagina);
		return "usuario/meus-anuncios";
	}
	

	@GetMapping("produtos/{status}")
	public String mostrarProdutosStatus(@PathVariable("status") String status, @RequestParam(defaultValue = "1") int page,  Model model, String pagina, Principal p) {
		Pageable pageable = PageRequest.of((page - 1), 28, Sort.by(Sort.Direction.DESC, "id"));
		Page<Produto> produtos = produtoRepository.findByUserUsernameAndStatus(p.getName(), StatusProduto.valueOf(status.toUpperCase()), pageable);
		model.addAttribute("produtos", produtos);
		
		model.addAttribute("status", status);
		
		User usermenu = userRepository.usernameAndFotoPerfil(SecurityContextHolder.getContext().getAuthentication().getName());
		model.addAttribute("perfil", usermenu);
		
		
		pagina = "produtos";
		model.addAttribute("pagina", pagina);
		return "usuario/meus-anuncios";
	}

	@GetMapping("relatorio")
	public String mostrarRelatorio(Model model, String pagina, Principal principal) {
		List<Produto> produtosAprovados = produtoRepository.findByUserUsernameAndStatus(principal.getName(), StatusProduto.APROVADO);
		List<Produto> produtosPendentes = produtoRepository.findByUserUsernameAndStatus(principal.getName(), StatusProduto.PENDENTE);
		
		model.addAttribute("aprovados", produtosAprovados.size());
		model.addAttribute("pendentes", produtosPendentes.size());
		
		User usermenu = userRepository.usernameAndFotoPerfil(SecurityContextHolder.getContext().getAuthentication().getName());
		model.addAttribute("perfil", usermenu);
		
		Pageable pageable = PageRequest.of(0, 2, Sort.by(Sort.Direction.DESC, "id"));
		Page<Comentario> comentarios = comentarioRepository.comentariosDosAnunciosDeCadaUser(principal.getName(), pageable);
		model.addAttribute("comentarios", comentarios);
		
		pagina = "relatorio";
		model.addAttribute("pagina", pagina);
		return "usuario/meu-relatorio";
	}
	// ========== COMENTÁRIO ==========
	@GetMapping("comentarios")
	public String comentarios(@RequestParam(defaultValue = "1") int page, RequisicaoRespostaComentario requisicao, Model model, String pagina) {
		
		Pageable pageable = PageRequest.of(page - 1 , 28, Sort.by(Sort.Direction.DESC, "id"));
		Page<Comentario> comentarios = comentarioRepository.comentariosDosAnunciosDeCadaUser(SecurityContextHolder.getContext().getAuthentication().getName(), pageable);
		model.addAttribute("comentarios", comentarios);
		
		User usermenu = userRepository.usernameAndFotoPerfil(SecurityContextHolder.getContext().getAuthentication().getName());
		model.addAttribute("perfil", usermenu);
		
		pagina = "comentario";
		model.addAttribute("pagina", pagina);
		return "usuario/comentarios";
	}
	
	@PostMapping("comentar")//Usuário comentar no produto
	public String comentar(@Valid RequisicaoNovoComentario requisicao, BindingResult result, Model model) {
		Produto produto = produtoRepository.findId(requisicao.getIdProduto());
		model.addAttribute("produto", produto);
		
		List<Comentario> comentarios = comentarioRepository.findByProduto(produto);
		model.addAttribute("comentarios", comentarios);
		
		model.addAttribute("user", produto.getUser());
		
		User usermenu = userRepository.usernameAndFotoPerfil(SecurityContextHolder.getContext().getAuthentication().getName());
		model.addAttribute("perfil", usermenu);
		
		if(result.hasErrors()) {
			return "produto";
		}
		
		User user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		
		Comentario comentario = requisicao.toComentario();
		comentario.setProduto(produto);
		comentario.setUser(user);
		
		comentarioRepository.save(comentario);
		
		return "redirect:/produtos/" + produto.getLink();
	}
	
	@PostMapping("comentarios")
	public String responder(@Valid RequisicaoRespostaComentario requisicao, BindingResult result, Model model) {
		
		if(result.hasErrors()) {
			return "usuario/comentarios";
		}
		
		User user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		Comentario comentario = comentarioRepository.findId(requisicao.getIdComentario());
		
		ComentarioReposta com = requisicao.toComentarioReposta();
		com.setComentario(comentario);
		com.setUser(user);
		comentarioRepostaRepository.save(com);
		
		return "redirect:/dashboard/comentarios";
	}
	
	// ========== ANÚNCIOS ==========
	
	@GetMapping("cadastrar-anuncio")
	public String mostrarCadastrarAnuncio(RequisicaoCadastrarAnuncio requisicao, Model model, String pagina) {
		
		User usermenu = userRepository.usernameAndFotoPerfil(SecurityContextHolder.getContext().getAuthentication().getName());
		model.addAttribute("perfil", usermenu);
		
		Contato contato = contatoRepository.findByUserUsername(usermenu.getUsername());
		if((contato.getWhatsapp() == null || contato.getWhatsapp().isEmpty()) || (contato.getFacebook() == null || contato.getFacebook().isEmpty())) {
			model.addAttribute("contatovazio", 0);
		}
		
		List<Categoria> categorias = categoriaRepository.findAll();
		model.addAttribute("categorias", categorias);
		
		List<Estado> estados = estadoRepository.findAll();
		model.addAttribute("estados", estados);
		
		pagina = "cadastrar";
		model.addAttribute("pagina", pagina);
		
		return "usuario/publicar-anuncio";
	}

	@PostMapping("cadastrar-anuncio")
	public String cadastrarAnuncio(@Valid RequisicaoCadastrarAnuncio requisicao, BindingResult result, Model model) throws IOException {
		
		User user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		model.addAttribute("perfil", user);
		
		List<Categoria> categorias = categoriaRepository.findAll();
		model.addAttribute("categorias", categorias);
		
		List<Estado> estados = estadoRepository.findAll();
		model.addAttribute("estados", estados);
		
		long soma = 0;
		for (MultipartFile file : requisicao.getFiles()) {
			soma += file.getSize();
		}
		
		if(result.hasErrors()) {
			if(soma > 8388608) model.addAttribute("erro", true);
			return "usuario/publicar-anuncio";
		}
		
		if(soma > 8388608) {
			model.addAttribute("erro", true);
			return "usuario/publicar-anuncio";
		}
		
		Produto produto = requisicao.toProduto();
		produto.setUser(user);
		
		Subcategoria subcategoria = subcategoriaRepository.findId(requisicao.getIdCategoria());
		produto.setSubcategoria(subcategoria);
		
		Cidade cidade = cidadeRepository.findByCidade(requisicao.getCidade());
		produto.setCidade(cidade);
		
		produtoRepository.save(produto);
		
			
		if(requisicao.getFiles().length > 0) {
			try {
				
				for(MultipartFile file : requisicao.getFiles()) {
					
					
					if(file.getSize() > 0) {
						byte[] bytes = file.getBytes();
						Imagem imagem = new Imagem(bytes);
						imagem.setProduto(produto);
						imagemRepository.save(imagem);
					}
				}
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return "redirect:/dashboard/produtos";
	}
	
	@GetMapping("editar-anuncio/{link}")
	public String mostrarEditarAnuncio(RequisicaoEditarAnuncio requisicao, @PathVariable("link") String link, Model model, String pagina) {
		
		User usermenu = userRepository.usernameAndFotoPerfil(SecurityContextHolder.getContext().getAuthentication().getName());
		model.addAttribute("perfil", usermenu);
		
		Produto produto = produtoRepository.findByLink(link);
		model.addAttribute("produto", produto);
		
		List<Imagem> imagens = imagemRepository.findByProduto(produto);
		model.addAttribute("imagens", imagens);
		
		List<Categoria> categorias = categoriaRepository.findAll();
		model.addAttribute("categorias", categorias);
		
		List<Estado> estados = estadoRepository.findAll();
		model.addAttribute("estados", estados);
		
		pagina = "cadastrar";
		model.addAttribute("pagina", pagina);
		
		if(!produto.getUser().getUsername().equals(usermenu.getUsername())) {
			return "redirect:/dashboard/produtos";
		}
		return "usuario/editar-anuncio";
	}
	
	@PostMapping("editar-anuncio")
	public String editarAnuncio(@Valid RequisicaoEditarAnuncio requisicao, BindingResult result, Model model) {
		
		User user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		model.addAttribute("perfil", user);
		
		List<Categoria> categorias = categoriaRepository.findAll();
		model.addAttribute("categorias", categorias);
		
		Produto produto = produtoRepository.findId(requisicao.getIdProduto());
		model.addAttribute("produto", produto);
		
		List<Imagem> imagens = imagemRepository.findByProduto(produto);
		model.addAttribute("imagens", imagens);
		
		List<Estado> estados = estadoRepository.findAll();
		model.addAttribute("estados", estados);
		
		
		long soma = 0;
		for (MultipartFile file : requisicao.getFiles()) {
			soma += file.getSize();
		}
		
		if(result.hasErrors()) {
			if(soma > 8388608) model.addAttribute("erro", true);
			return "usuario/editar-anuncio";
		}
		
		if(soma > 8388608) {
			model.addAttribute("erro", true);
			return "usuario/editar-anuncio";
		}
		
		produto.setNome(requisicao.getNome());
		produto.setCondicao(Condicao.valueOf(requisicao.getCondicao()));
		produto.setPrecoVista(new BigDecimal(requisicao.getPrecoVista()));
		produto.setDescricao(requisicao.getDescricao());
		BigDecimal b2 = new BigDecimal(requisicao.getPrecoVista());
		
		if(!requisicao.getPrecoCortado().isEmpty()) {
			BigDecimal b1 = new BigDecimal(requisicao.getPrecoCortado());
			if(b1.compareTo(b2) == 1) {
				produto.setPrecoCortado(new BigDecimal(requisicao.getPrecoCortado()));
			}
		}
		
		if(!requisicao.getPrecoCartao().isEmpty()) {
			BigDecimal b3 = new BigDecimal(requisicao.getPrecoCartao());
			if(b2.compareTo(b3) == 2 || b2.compareTo(b3) == 0) {
				produto.setPrecoCartao(new BigDecimal(requisicao.getPrecoCartao()));
				produto.setParcelasCartao(requisicao.getParcelasCartao());
			} else {
				produto.setParcelasCartao(1);
			}
		}
		
		Subcategoria subcategoria = subcategoriaRepository.findId(requisicao.getIdCategoria());
		produto.setSubcategoria(subcategoria);
		
		Cidade cidade = cidadeRepository.findByCidade(requisicao.getCidade());
		produto.setCidade(cidade);
		
		
		if(requisicao.getFiles().length > 0) {
			
			try {
				int i = 0;
				
				for(MultipartFile file : requisicao.getFiles()) {
					
					
					if(file.getSize() > 0) {
						i++;
						byte[] bytes = file.getBytes();
						Imagem imagem = new Imagem(bytes);
						imagem.setProduto(produto);
						imagemRepository.save(imagem);
//						imagemList.add(imagem);
						
						if (i == 1 && (produto.getImagem() == null)) {
							produto.setImagem(bytes);
						}
					}
				}
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} 
		
//		produto.setImagens(imagemList);
		produtoRepository.save(produto);
		return "redirect:/dashboard/editar-anuncio/" + produto.getLink();
	}
	
	
	@PostMapping("deletarAnuncio")
	public String deletarAnuncio(@RequestParam("id") Long id) {
		Produto produto = produtoRepository.findId(id);
		
		produtoRepository.delete(produto);
		return "redirect:/dashboard/produtos";
	}
	
	@PostMapping("desativarAnuncio")
	public String desativarAnuncio(@RequestParam("id") Long id) {
		Produto produto = produtoRepository.findId(id);
		produto.setStatus(StatusProduto.DESATIVADO);
		produtoRepository.save(produto);
		return "redirect:/dashboard/produtos";
	}
	
	@PostMapping("ativarAnuncio")
	public String ativarAnuncio(@RequestParam("id") Long id) {
		Produto produto = produtoRepository.findId(id);
		produto.setStatus(StatusProduto.APROVADO);
		produtoRepository.save(produto);
		return "redirect:/dashboard/produtos";
	}
	
	// ========== FAVORITOS ==========
	@GetMapping("favoritos")
	public String mostrarFavoritos(@RequestParam(defaultValue = "1") int page, Model model, String pagina) {
		Pageable pageable = PageRequest.of(page - 1, 28, Sort.by(Sort.Direction.DESC, "id")) ;
		Page<Favorito> favoritos = favoritoRepository.findByUserUsername(SecurityContextHolder.getContext().getAuthentication().getName(), pageable);
		model.addAttribute("favoritos", favoritos);
		
		User usermenu = userRepository.usernameAndFotoPerfil(SecurityContextHolder.getContext().getAuthentication().getName());
		model.addAttribute("perfil", usermenu);
		
		pagina = "favoritos";
		model.addAttribute("pagina", pagina);
		
		return "usuario/meus-favoritos";
	}
	
	@PostMapping("favoritar")
	public String adicionarRemoverFavorito(@RequestParam("idProduto") Long id, @RequestParam("pagina") String pagina) {
		Produto produto = produtoRepository.findId(id);
		User user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		Favorito favoritoChecar = favoritoRepository.findByProdutoAndUserUsername(produto, user.getUsername());
		
		if(favoritoChecar == null) {
			Favorito favorito = new Favorito();
			favorito.setProduto(produto);
			favorito.setUser(user);
			favoritoRepository.save(favorito);
		} else {
			favoritoRepository.delete(favoritoChecar);
		}
		
		if(pagina.equals("listagem")) {
			return "redirect:/produtos";
		} else if(pagina.equals("favoritos")) {
			return "redirect:/dashboard/favoritos";
		} else if(pagina.equals("produto")) {
			return "redirect:/produtos/" + produto.getLink();
		} else if(pagina.equals("home")) {
			return "redirect:/inicio";
		}
		
		return null;
	}
	
	
	// ========== CONFIGURAÇÕES ===========

	@GetMapping("configuracoes")
	public String configuracoes(RequisicaoAtualizarUsuario requisicao, RequisicaoAtualizarContato requisicao2, RequisicaoAtualizarPrivacidade requisicao3,  Model model, String pagina, String paginaInfo) {
		
		model.addAttribute("paginaInfo", paginaInfo);
		
		User usermenu = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		model.addAttribute("perfil", usermenu);
		
		List<Estado> estados = estadoRepository.findAll();
		model.addAttribute("estados", estados);
		
		Contato contato = contatoRepository.findByUserUsername(usermenu.getUsername());
		if((contato.getWhatsapp() == null || contato.getWhatsapp().isEmpty()) || (contato.getFacebook() == null || contato.getFacebook().isEmpty())) {
			model.addAttribute("contatovazio", 0);
		}
		model.addAttribute("contato", contato);
		
		pagina = "configuracoes";
		model.addAttribute("pagina", pagina);
		
		return "usuario/configuracoes";
	}
	
	@PostMapping("/atualizarCadastro")
	public String atualizar(@Valid RequisicaoAtualizarUsuario requisicao, BindingResult result, RequisicaoAtualizarContato requisicao2, RequisicaoAtualizarPrivacidade requisicao3, Model model, Principal principal) {
		User user = userRepository.findByUsername(principal.getName());
		model.addAttribute("perfil", user);
		
		if(result.hasErrors()) {
			return configuracoes(requisicao, requisicao2, requisicao3, model, "configuracoes", "1");
		}
		
		Cidade cidade = cidadeRepository.findByCidade(requisicao.getCidade());
		
		model.addAttribute("paginaInfo", "1");
		user.setNome(requisicao.getNome());
		user.setSobrenome(requisicao.getSobrenome());
		user.setDescricao(requisicao.getDescricao());
		user.setCidade(cidade);
		userRepository.save(user);
		
		return "redirect:/dashboard/configuracoes";
	}
	
	@PostMapping("/atualizarContato")
	public String atualizarContato(@Valid RequisicaoAtualizarContato requisicao, BindingResult result, RequisicaoAtualizarUsuario requisicao2, RequisicaoAtualizarPrivacidade requisicao3, Model model, Principal principal) {
		Contato contato = contatoRepository.findByUserUsername(principal.getName());
		model.addAttribute("contato", contato);
		
		if(result.hasErrors()) {
			return configuracoes(requisicao2, requisicao, requisicao3, model, "configuracoes", "2");
		}
		
		model.addAttribute("paginaInfo", "2");
		contato.setEmail(requisicao.getEmailContato());
		contato.setFacebook(requisicao.getFacebook());
		contato.setInstagram(requisicao.getInstagram());
		contato.setWhatsapp(requisicao.getWhatsapp());
		contatoRepository.save(contato);
		
		return "redirect:/dashboard/configuracoes";
	}
	
	@PostMapping("/atualizarPrivacidade")
	public String atualizarPrivacidade(@Valid RequisicaoAtualizarPrivacidade requisicao, BindingResult result, RequisicaoAtualizarUsuario requisicao2, RequisicaoAtualizarContato requisicao3,  Model model, Principal principal) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		User user = userRepository.findByUsername(principal.getName());
		
		if ((requisicao.getEmail().equals(user.getEmail()))
				&& encoder.matches(requisicao.getSenha(), user.getPassword())) {
			
			user.setPassword(encoder.encode(requisicao.getNovaSenha()));
			
		} else {
			
			model.addAttribute("erro", "Seu Email ou Senha estão incorretos");
			return configuracoes(requisicao2, requisicao3, requisicao, model, "configuracoes", "3");
			
		}
		
		if(result.hasErrors()) {
			return configuracoes(requisicao2, requisicao3, requisicao, model, "configuracoes", "3");
		}

		userRepository.save(user);
		
		return "redirect:/dashboard/configuracoes";
	}
	
	// ========== IMAGENS ==========
	

	@PostMapping("uploadPerfil")
	public String uploadPerfil(@RequestParam("file") MultipartFile file, Principal principal, RequisicaoAtualizarUsuario requisicao, RequisicaoAtualizarContato requisicao2, RequisicaoAtualizarPrivacidade requisicao3,  Model model) {
		
		User user = userRepository.findByUsername(principal.getName());
		
		if(file.getSize() > 3145728) {
			model.addAttribute("erroPerfil", true);
			return configuracoes(requisicao, requisicao2, requisicao3, model, "configuracoes", "1");
		}
		
		if(!file.isEmpty()) {
			try {
				
				byte[] bytes = file.getBytes();
				user.setFotoPerfil(bytes);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		userRepository.save(user);
		return "redirect:/dashboard/configuracoes";
	}
	
	@PostMapping("uploadCapa")
	public String uploadCapa(@RequestParam("file") MultipartFile file, Principal principal, RequisicaoAtualizarUsuario requisicao, RequisicaoAtualizarContato requisicao2, RequisicaoAtualizarPrivacidade requisicao3,  Model model) {
		
		User user = userRepository.findByUsername(principal.getName());
		
		if(file.getSize() > 3145728) {
			model.addAttribute("erroCapa", true);
			return configuracoes(requisicao, requisicao2, requisicao3, model, "configuracoes", "1");
		}
		
		if(!file.isEmpty()) {
			try {
				
				byte[] bytes = file.getBytes();
				user.setFotoCapa(bytes);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		userRepository.save(user);
		return "redirect:/dashboard/configuracoes";
	}
	
	@PostMapping("deletarImagem")
	public String deletarImagemAnuncio(@RequestParam("idImagem") Long idImagem, @RequestParam("idProduto") Long idProduto) {
		Imagem imagem = imagemRepository.findId(idImagem);
		imagemRepository.delete(imagem);
		
		Produto produto = produtoRepository.findId(idProduto);
		
		if(produto.getImagens().size() > 0) {
			int i = 0;
			for(Imagem img : produto.getImagens()){
				if(i == 0) {
					produto.setImagem(img.getImagem());
				}
				
				i++;
			}
		} else {
			produto.setImagem(null);
		}
		produtoRepository.save(produto);
		
		
		return "redirect:/dashboard/editar-anuncio/" + produto.getLink();
	}
	
}
