package br.com.casadocodigo.loja.conf;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @EnableTransactionManagement
	Habilitando a TRANSACTION para o SPRING tomar conta
 * */
@EnableTransactionManagement
public class JPAConfiguration {

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(){
		LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
		
		JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
				
//		Adaptador do JPA que iremos utilizar
		factoryBean.setJpaVendorAdapter(vendorAdapter);
		
//		DataSource - gerencia as conexoes para nos
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setUsername("root");
		dataSource.setPassword("");
		dataSource.setUrl("jdbc:mysql://localhost:3306/casadocodigo");
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		
		factoryBean.setDataSource(dataSource);
		
//		Classe responsavel por salvar propriedades do Hibernate
		Properties props = new Properties();
		props.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
		props.setProperty("hibernate.show_sql", "true");
//		hbm2ddl - hibernate mapping ou mapeamento do JPA '2' (to/para) ddl
//		ou seja, toda vez que mudar o modelo, o hibernate mude/gerencie o banco
//		para nao nos preocuparmos com ter de realizar a alteracao manualmente
		props.setProperty("hibernate.hbm2ddl.auto", "update");
		
		factoryBean.setJpaProperties(props);
		
//		Falar onde que estao cada ENTIDADE pro Hibernate poder escanear/procurar por essas
//		entidades
		factoryBean.setPackagesToScan("br.com.casadocodigo.loja.models");
	
		return factoryBean;
	}
	
	
	/**
	 * Metodo que sera um BEAN do Spring (de responsabilidade do Spring)
	 * pra que ele seja reconhecido pelo Spring como o CRIADOR da TRANSACTION
	 * */
	@Bean
	public JpaTransactionManager transactionManager(EntityManagerFactory emf) {
		return new JpaTransactionManager(emf);
	}
	
}
