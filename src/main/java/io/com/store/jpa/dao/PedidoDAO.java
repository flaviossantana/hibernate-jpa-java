package io.com.store.jpa.dao;

import io.com.store.jpa.dao.repository.PedidoRepository;
import io.com.store.jpa.entity.Pedido;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

public class PedidoDAO implements PedidoRepository {

    private EntityManager entityManager;

    public PedidoDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void salvar(Pedido pedido) {
        entityManager.persist(pedido);
    }

    @Override
    public void atualizar(Pedido pedido) {
        entityManager.merge(pedido);
    }

    @Override
    public void excluir(Pedido pedido) {
        pedido = entityManager.merge(pedido);
        entityManager.remove(pedido);
    }

    @Override
    public Pedido buscarPorId(long id) {
        return entityManager.find(Pedido.class, id);
    }

    @Override
    public List<Pedido> buscarTodos() {
        return entityManager
                .createQuery("SELECT p FROM Pedido p", Pedido.class)
                .getResultList();
    }

    @Override
    public BigDecimal valorTotalDeTodasAsVendas() {
        String jpql = "SELECT SUM(p.valorTotal) FROM Pedido p";
        return entityManager.createQuery(jpql, BigDecimal.class)
                .getSingleResult();
    }


}
