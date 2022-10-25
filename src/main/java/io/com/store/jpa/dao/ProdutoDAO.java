package io.com.store.jpa.dao;

import io.com.store.jpa.dao.repository.ProdutoRepository;
import io.com.store.jpa.entity.Produto;

import javax.persistence.EntityManager;

public class ProdutoDAO implements ProdutoRepository {

    private EntityManager entityManager;

    public ProdutoDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void salvar(Produto produto) {
        entityManager.persist(produto);
    }

}
