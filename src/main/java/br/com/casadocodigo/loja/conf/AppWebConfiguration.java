package br.com.casadocodigo.loja.conf;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.format.datetime.DateFormatterRegistrar;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import br.com.casadocodigo.loja.controllers.HomeController;
import br.com.casadocodigo.loja.dao.ProdutoDAO;
import br.com.casadocodigo.loja.infra.FileSaver;

@EnableWebMvc
@ComponentScan(basePackageClasses={HomeController.class, ProdutoDAO.class, FileSaver.class})
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
	
	/**
	 * método responsável por configurar o pattern para os 
	 * objetos "Data" de toda a aplicação para não termos necessidade
	 * de anotar em todos os objetos que forem declarados
	 * */
	@Bean
	public FormattingConversionService mvcConversionService() {
		DefaultFormattingConversionService conversionService 
				= new DefaultFormattingConversionService();
		DateFormatterRegistrar registrar = new DateFormatterRegistrar();
		registrar.setFormatter(new DateFormatter("dd/MM/yyyy"));
		registrar.registerFormatters(conversionService);
		
		return conversionService;
	}
	
	/** "java.lang.IllegalArgumentException: Expected MultipartHttpServletRequest: 
	 *   is a MultipartResolver configured?"
	 *   
	 *   Erro estourado antes da criacao do metodo abaixo. Esse metodo sera responsavel
	 *   por uma nova configuracao: uma instancia que temos que criar para resolver os arquivos
	 *   de multiplos formatos
	 * */
	@Bean
	public MultipartResolver multipartResolver() {
		return new StandardServletMultipartResolver();
	}
	
}
