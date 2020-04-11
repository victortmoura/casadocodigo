package br.com.casadocodigo.loja.conf;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import br.com.casadocodigo.loja.controllers.HomeController;
import br.com.casadocodigo.loja.dao.ProdutoDAO;

@EnableWebMvc
@ComponentScan(basePackageClasses={HomeController.class, ProdutoDAO.class})
public class AppWebConfiguration {

	
	/**
	 * Metodo que retorna "InternalResourceViewResolver"
	classe responsavel por configurar o caminho do PREFIXO e SUFIXO
	das views. (Resolvedor Interno das Views)
	
	@Bean - O spring vai querer que a gente diga que ESSE METODO vai gerar um arquivo/
	uma classe que ele conhece, que ele vai usar pra configuracao.
	Logo, toda classe gerenciada pelo spring e' um BEAN
	 * */
	@Bean
	public InternalResourceViewResolver internalResourceViewResolver(){
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		
		return resolver;
	}
	
	/**
	 * Método responsável por configurar o arquivo "message.properties" que 
	 * está na pasta do WEB-INF, o qual contém as mensagens de validação
	 * */
	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageResource 
				= new ReloadableResourceBundleMessageSource();
		messageResource.setBasename("/WEB-INF/message");
		messageResource.setDefaultEncoding("UTF-8");
		messageResource.setCacheSeconds(1);
		
		return messageResource;
	}
	
}
