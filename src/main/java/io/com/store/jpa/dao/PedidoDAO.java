package io.com.store.jpa.dao;

import io.com.store.jpa.dao.repository.PedidoRepository;
import io.com.store.jpa.entity.Pedido;

import javax.persistence.EntityManager;
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
    public List<Object[]> relatorioDeProdutoQuantidadeEUltimaVenda() {

        StringBuilder jpql = new StringBuilder();
        jpql.append("SELECT prd.nome, SUM(itn.quantidade), MAX(pdd.data) ");
        jpql.append("FROM Pedido pdd ");
        jpql.append("JOIN pdd.itens itn ");
        jpql.append("JOIN itn.produto prd ");
        jpql.append("GROUP BY prd.nome ");
        jpql.append("ORDER BY SUM(itn.quantidade) DESC ");

        return entityManager
                .createQuery(jpql.toString(), Object[].class)
                .getResultList();
    }

}
