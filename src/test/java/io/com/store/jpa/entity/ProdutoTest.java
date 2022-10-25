package io.com.store.jpa.entity;

import io.com.store.jpa.builder.ProdutoBuilder;
import io.com.store.jpa.dao.CategoriaDAO;
import io.com.store.jpa.dao.ProdutoDAO;
import io.com.store.jpa.dao.repository.CategoriaRepository;
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

        Categoria smartphone = new Categoria("SMARTPHONE");


        Produto produto = ProdutoBuilder.init()
                .nome("GALAXY S21+")
                .descricao("SMARTPHONE SAMSUMG GALAXY S21+ 256GB")
                .preco("1000.00")
                .categoria(smartphone)
                .build();



        EntityManager em = JPAUtil.getEntityManager();
        ProdutoRepository produtoRepository = new ProdutoDAO(em);
        CategoriaRepository categoriaRepository = new CategoriaDAO(em);

        em.getTransaction().begin();
        categoriaRepository.salvar(smartphone);
        produtoRepository.salvar(produto);
        em.getTransaction().commit();
        em.close();

        assertNotNull(produto.getId());
    }

    public void testCicloDeVidaJPA() {

        Produto produto = ProdutoBuilder.init()
                .nome("GALAXY S21+")
                .descricao("SMARTPHONE SAMSUMG GALAXY S21+ 256GB")
                .preco("1000.00")
                .build();

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("store-jpa");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();

        //IRÁ INSERIR UM NOVO REGISTRO NO BANCO DE DADOS (INSERT)
        entityManager.persist(produto);

        //IRÁ FAZER UM UPDATE NO BANCO DE DADOS (UPDATE)
        produto.setNome("GALAXY S21+ ULTRA");

        entityManager.flush();
        entityManager.clear();

        //IRÁ DEVOLVER O OBJETO NO ESTADO MANAGED (SELECT)
        produto = entityManager.merge(produto);

        //IRÁ FAZER UM UPDATE NO BANCO DE DADOS (UPDATE)
        produto.setDescricao("SMARTPHONE SAMSUMG GALAXY S21+ ULTRA 256GB 12GB RAM");
        entityManager.flush();

        //IRÁ COLOCAR NO ESTADO DE DETACHED
        entityManager.clear();

        //IRÁ DEVOLVER O OBJETO NO ESTADO MANAGED (SELECT)
        produto = entityManager.merge(produto);

        //IRÁ COLOCAR O OBJETO NO ESTADO REMOVED (DELETE)
        entityManager.remove(produto);

        //IRÁ FAZER UM COMMIT NO BANCO DE DADOS
        entityManager.flush();

        assertNotNull(produto.getId());
    }

}
