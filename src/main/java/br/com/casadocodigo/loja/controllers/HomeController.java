package br.com.casadocodigo.loja.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

	/**
	 * Os metodos anotados com RequestMapping mapeiam qual view
	   sera requisitada atraves do path passado no parametro da annotation
	 * */
	@RequestMapping("/")
	public String index(){
		System.out.println("Entrando na Home");
		return "home";
	}
	
}
