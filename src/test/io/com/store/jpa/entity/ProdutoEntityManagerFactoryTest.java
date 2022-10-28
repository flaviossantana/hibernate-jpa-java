package io.com.store.jpa.entity;

import io.com.store.jpa.builder.ProdutoBuilder;
import io.com.store.jpa.entity.produto.Produto;
import junit.framework.TestCase;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ProdutoEntityManagerFactoryTest extends TestCase {

    public void testCicloDeVidaJPAComEntityManagerFactory() {

        Produto produto = ProdutoBuilder.init()
                .nome("GALAXY S21+ EMF")
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

    public void testDeveriaCadastarProdutoViaEntityManagerFactory() {
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

}
