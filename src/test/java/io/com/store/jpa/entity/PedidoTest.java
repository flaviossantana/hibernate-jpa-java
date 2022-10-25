package io.com.store.jpa.entity;

import io.com.store.jpa.builder.ClienteBuilder;
import io.com.store.jpa.builder.PedidoBuilder;
import io.com.store.jpa.builder.ProdutoBuilder;
import io.com.store.jpa.dao.PedidoDAO;
import io.com.store.jpa.dao.repository.PedidoRepository;
import io.com.store.jpa.dao.util.JPAUtil;
import junit.framework.TestCase;

import javax.persistence.EntityManager;

public class PedidoTest extends TestCase {

    private Produto galaxyS21Plus;
    private Produto galaxyS21Ultra;
    private Cliente joaoDaSilva;

    public void setUp() throws Exception {
        EntityManager em = JPAUtil.getEntityManager();
        em.getTransaction().begin();
        criarProdutos(em);
        criarCliente(em);
        em.getTransaction().commit();
        em.close();

    }

    private void criarCliente(EntityManager em) {
        joaoDaSilva = ClienteBuilder
                .init()
                .nome("João da Silva")
                .cpf("55412287450")
                .persisted(em)
                .build();
    }

    private void criarProdutos(EntityManager em) {
        criarProdutoGalaxyS21Plus(em);
        criarProdutoGalaxyS21Ultra(em);
    }

    private void criarProdutoGalaxyS21Ultra(EntityManager em) {
        galaxyS21Ultra = ProdutoBuilder.init()
                .nome("GALAXY S21 Ultra")
                .descricao("SMARTPHONE SAMSUMG GALAXY S21 Ultra 512GB")
                .preco("5000.00")
                .nomeCategoria("SMARTPHONES")
                .persisted(em)
                .build();
    }

    private void criarProdutoGalaxyS21Plus(EntityManager em) {
        galaxyS21Plus = ProdutoBuilder.init()
                .nome("GALAXY S21+")
                .descricao("SMARTPHONE SAMSUMG GALAXY S21+ 256GB")
                .preco("1000.00")
                .nomeCategoria("SMARTPHONES")
                .persisted(em)
                .build();
    }

    public void tearDown() throws Exception {
    }

    public void testDeveriaInstanciarPedido() {

        Pedido pedido = PedidoBuilder
                .init()
                .cliente(joaoDaSilva)
                .item(1, galaxyS21Plus)
                .item(2, galaxyS21Ultra)
                .build();

        EntityManager entityManager = JPAUtil.getEntityManager();

        entityManager.getTransaction().begin();
        PedidoRepository pedidoRepository = new PedidoDAO(entityManager);
        pedidoRepository.salvar(pedido);
        entityManager.flush();

    }
}
