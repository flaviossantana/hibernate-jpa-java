package io.com.store.jpa.dao;

import io.com.store.jpa.dao.repository.CategoriaRepository;
import io.com.store.jpa.entity.Categoria;

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

    @Override
    public void atualizar(Categoria categoria) {
        entityManager.merge(categoria);
    }

    @Override
    public void excluir(Categoria categoria) {
        categoria = entityManager.merge(categoria);
        entityManager.remove(categoria);
    }

}
