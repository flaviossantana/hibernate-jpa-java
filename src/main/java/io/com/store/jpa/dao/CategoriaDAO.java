package io.com.store.jpa.dao;

import io.com.store.jpa.dao.repository.CategoriaRepository;
import io.com.store.jpa.entity.categoria.Categoria;
import io.com.store.jpa.entity.categoria.CategoriaID;
import io.com.store.jpa.entity.pessoa.Cliente;

import javax.persistence.EntityManager;

public class CategoriaDAO implements CategoriaRepository {

    private EntityManager entityManager;

    public CategoriaDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Categoria buscarPorId(CategoriaID id) {
        return entityManager.find(Categoria.class, id);
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
