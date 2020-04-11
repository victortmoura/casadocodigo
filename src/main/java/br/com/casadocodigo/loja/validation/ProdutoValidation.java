package br.com.casadocodigo.loja.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import br.com.casadocodigo.loja.models.Produto;

public class ProdutoValidation implements Validator {
	
	/**
	 * O metodo é usado para saber se a gente suporta a
	 * classe que está sendo enviada.
	 * "isAssignableFrom()" - se o produto é validado
	 * ele pode ser aceito através dessa classe.
	 * */
	@Override
	public boolean supports(Class<?> clazz) {
		return Produto.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "titulo", "field.required");
		ValidationUtils.rejectIfEmpty(errors, "descricao", "field.required");
		
		Produto produto = (Produto) target;
		
		if(produto.getPaginas() <= 0) {
			errors.rejectValue("paginas", "field.required");
		}
		
	}
}
