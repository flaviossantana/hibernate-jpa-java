package io.com.store.jpa.entity;

import io.com.store.jpa.builder.ProdutoBuilder;
import io.com.store.jpa.dao.ProdutoDAO;
import io.com.store.jpa.dao.repository.ProdutoRepository;
import io.com.store.jpa.dao.util.JPAUtil;
import junit.framework.TestCase;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ProdutoTest extends TestCase {

    public void testDeveriaCadastarProduto() {

        Produto produto = ProdutoBuilder.init()
                .nome("GALAXY S21+")
                .descricao("SMARTPHONE SAMSUMG GALAXY S21+ 256GB")
                .preco("1000.00")
                .build();

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("store-jpa");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();
        entityManager.persist(produto);
        entityManager.getTransaction().commit();
        entityManager.close();

        assertNotNull(produto.getId());
    }

    public void testDeveriaCadastarProdutoUsandoProdutoDAO() {

        Produto produto = ProdutoBuilder.init()
                .nome("GALAXY S21+")
                .descricao("SMARTPHONE SAMSUMG GALAXY S21+ 256GB")
                .preco("1000.00")
                .build();

        EntityManager em = JPAUtil.getEntityManager();
        ProdutoRepository produtoRepository = new ProdutoDAO(em);

        em.getTransaction().begin();
        produtoRepository.salvar(produto);
        em.getTransaction().commit();
        em.close();

        assertNotNull(produto.getId());
    }

    public void testDeveriaCadastarProdutoComCategoria() {

        Produto produto = ProdutoBuilder.init()
                .nome("GALAXY S21+")
                .descricao("SMARTPHONE SAMSUMG GALAXY S21+ 256GB")
                .preco("1000.00")
                .categoria(Categoria.CELULARES)
                .build();

        EntityManager em = JPAUtil.getEntityManager();
        ProdutoRepository produtoRepository = new ProdutoDAO(em);

        em.getTransaction().begin();
        produtoRepository.salvar(produto);
        em.getTransaction().commit();
        em.close();

        assertNotNull(produto.getId());
    }

}
