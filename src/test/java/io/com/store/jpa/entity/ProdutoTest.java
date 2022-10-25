package io.com.store.jpa.entity;

import io.com.store.jpa.builder.ProdutoBuilder;
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

}
