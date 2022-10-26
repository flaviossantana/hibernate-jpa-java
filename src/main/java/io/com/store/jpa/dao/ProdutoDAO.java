package io.com.store.jpa.dao;

import io.com.store.jpa.dao.repository.ProdutoRepository;
import io.com.store.jpa.entity.Produto;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

public class ProdutoDAO implements ProdutoRepository {

    private EntityManager entityManager;

    public ProdutoDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void salvar(Produto produto) {
        entityManager.persist(produto);
    }

    @Override
    public void atualizar(Produto produto) {
        entityManager.merge(produto);
    }

    @Override
    public void excluir(Produto produto) {
        produto = entityManager.merge(produto);
        entityManager.remove(produto);
    }

    @Override
    public Produto buscarPorId(long id) {
        return entityManager.find(Produto.class, id);
    }

    @Override
    public List<Produto> buscarTodos() {
        return entityManager
                .createQuery("SELECT p FROM Produto p", Produto.class)
                .getResultList();
    }

    @Override
    public List<Produto> buscarPorNome(String nome) {
        return entityManager
                .createQuery("SELECT p FROM Produto p WHERE p.nome = :nome", Produto.class)
                .setParameter("nome", nome)
                .getResultList();
    }

    @Override
    public List<Produto> buscarPorNomeCategoria(String nome) {
        return entityManager
                .createQuery("SELECT p FROM Produto p WHERE p.categoria.nome = :nome", Produto.class)
                .setParameter("nome", nome)
                .getResultList();
    }

    @Override
    public BigDecimal buscarPrecoPorNome(String nome) {
        return entityManager
                .createQuery("SELECT p.preco FROM Produto p WHERE p.nome = :nome", BigDecimal.class)
                .setParameter("nome", nome)
                .getSingleResult();
    }

    @Override
    public BigDecimal buscarPrecoPorNomeNamedQuery(String nome) {
        return entityManager
                .createNamedQuery("produto.buscarPrecoPorNome", BigDecimal.class)
                .setParameter("nome", nome)
                .getSingleResult();
    }

}
