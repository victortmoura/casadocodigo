package br.com.casadocodigo.loja.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.casadocodigo.loja.models.Produto;

/**
@Repository Na hora que estamos usando nosso DAO, a gente ta pedindo pro SPRING injetar, 
	 mas o spring so consegue injetar objetos que ele conhece, que ele gerencia,
	 se ele nao conhecer o ProdutoDAO, ele nao vai injetar.
	 Entao precisamos anotar a classe com @Repository para fazer com que o SPRING
 conhe�a de fato a classe.
 
 @Transacional O Spring vai fazer a transacao do Objeto
 */
@Repository
@Transactional
public class ProdutoDAO {

	/**
	 * Objeto responsavel por GERENCIAR as ENTIDADES
		@PersistenceContext O Spring precisa injetar o
		EntityManager na classe atraves da anotacao
	*/
	@PersistenceContext
	private EntityManager manager;
	
	public void gravar(Produto produto) {
		manager.persist(produto);
	}

	public List<Produto> listar() {
		return manager.createQuery("SELECT p FROM Produto p", Produto.class)
				.getResultList();
	}
	
}
