package io.com.store.jpa.dao;

import io.com.store.jpa.dao.repository.CategoriaRepository;
import io.com.store.jpa.entity.Categoria;
import io.com.store.jpa.entity.Produto;

import javax.persistence.EntityManager;

public class CategoriaDAO implements CategoriaRepository {

    private EntityManager entityManager;

    public CategoriaDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void salvar(Categoria categoria) {
        entityManager.persist(categoria);
    }

}
