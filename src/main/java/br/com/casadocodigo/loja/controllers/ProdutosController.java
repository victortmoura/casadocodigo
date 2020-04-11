package br.com.casadocodigo.loja.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.casadocodigo.loja.dao.ProdutoDAO;
import br.com.casadocodigo.loja.models.Produto;
import br.com.casadocodigo.loja.models.TipoPreco;
import br.com.casadocodigo.loja.validation.ProdutoValidation;

@Controller
@RequestMapping("produtos")
public class ProdutosController {

	//Anotacao responsavel por injetar o DAO dentro do Controller
	@Autowired
	private ProdutoDAO dao;
	
	/**
	 * Vai ligar nosso validador com o produto
	 * */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(new ProdutoValidation());
	}

	@RequestMapping("form")
	public ModelAndView form() {
		ModelAndView modelAndView = new ModelAndView("produtos/form");
		modelAndView.addObject("tipos", TipoPreco.values());
		return modelAndView;
	}
	
/**
 * OBS: O método gravar, além do objeto em questão (que irá ser persistido)
	espera mais dois parâmetros: BindingResult e RedirectAttributes.
	1. O primeiro é o objeto que  vai validar se o produto está ou não correto
	2. Já o segundo vai fazer o redirecionamento após o fluxo terminar
	A questão é: a ordem dos parâmetros IMPORTA isso porque o Spring considera
	que se está querendo validar o Produto, o resultado dessa validação tem que vir
	logo depois
	Com Validator do Spring, temos a possibilidade de configurar o controller 
	para que utilize automaticamente o validador todas as vezes que for
	necessário validar a classe desejada, ou seja, a classe que precisa ser 
	validada (anotada com @Valid). Vale lembrar que o uso do InitBinder é
	 necessário para que seja possível essa validação automática.
 * */
	@RequestMapping(method=RequestMethod.POST)
	public ModelAndView gravar(@Valid Produto produto, BindingResult result, 
			RedirectAttributes redirectAttributes){
		
		if(result.hasErrors()) {
			return form();
		}
		
		dao.gravar(produto);

//		O Flash Scoped é um escopo onde os objetos que adicionamos nele através do método 
//		addFlashAttribute ficam vivos de um request para outro
		redirectAttributes.addFlashAttribute("sucesso", "Produto cadastrado com sucesso");
		
//		OBS: Para evitar qualquer problema de dados reenviados, realizamos um redirect 
//		após um formulário com POST.
//		Pois ao fazer F5 o navegador repete o ultimo request que ele realizou, e 
//		quando esse resquest é um POST, todos os dados que foram enviados também
//		são repetidos. 
		return  new ModelAndView("redirect:produtos");
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView listar() {
		List<Produto> produtos = dao.listar();
		ModelAndView modelAndView = new ModelAndView("produtos/lista");
		modelAndView.addObject("produtos", produtos);
		
		return modelAndView;
	}
}
