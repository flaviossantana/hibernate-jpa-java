package io.com.store.jpa.entity;

import io.com.store.jpa.builder.ClienteBuilder;
import io.com.store.jpa.builder.PedidoBuilder;
import io.com.store.jpa.builder.ProdutoBuilder;
import io.com.store.jpa.dao.PedidoDAO;
import io.com.store.jpa.dao.repository.PedidoRepository;
import io.com.store.jpa.dao.util.JPAUtil;
import io.com.store.jpa.entity.pessoa.Cliente;
import io.com.store.jpa.entity.produto.Produto;
import junit.framework.TestCase;

import javax.persistence.EntityManager;

public class PerformanceTest extends TestCase {

    private Produto galaxyS21Plus;
    private Produto galaxyS21Ultra;
    private Cliente joaoDaSilva;
    private EntityManager em;

    public void setUp() {
        this.em = JPAUtil.getEntityManager();
        this.em.getTransaction().begin();
        criarCliente();
        criarProdutos();
        this.em.getTransaction().commit();
    }

    private void criarCliente() {
        joaoDaSilva = ClienteBuilder
                .init()
                .nome("João da Silva")
                .cpf("55412287450")
                .persisted(this.em)
                .build();
    }

    private void criarProdutos() {
        criarProdutoGalaxyS21Plus();
        criarProdutoGalaxyS21Ultra();
    }

    private void criarProdutoGalaxyS21Ultra() {
        galaxyS21Ultra = ProdutoBuilder.init()
                .nome("GALAXY S21 Ultra")
                .descricao("SMARTPHONE SAMSUMG GALAXY S21 Ultra 512GB")
                .preco("5000.00")
                .nomeCategoria("SMARTPHONES")
                .persisted(this.em)
                .build();
    }

    private void criarProdutoGalaxyS21Plus() {
        galaxyS21Plus = ProdutoBuilder.init()
                .nome("GALAXY S21+")
                .descricao("SMARTPHONE SAMSUMG GALAXY S21+ 256GB")
                .preco("1000.00")
                .nomeCategoria("SMARTPHONES")
                .persisted(this.em)
                .build();
    }

    public void testDeveriaFazerConsultaSemFazerJoinsDesnecessarios() {
        Pedido pedido = PedidoBuilder.init()
                .cliente(joaoDaSilva)
                .item(10, galaxyS21Plus)
                .item(5, galaxyS21Ultra)
                .build();

        this.em.getTransaction().begin();
        PedidoRepository pedidoDAO = new PedidoDAO(em);
        pedidoDAO.salvar(pedido);
        this.em.flush();

        Pedido pedidoRecuperado = pedidoDAO.buscarPorId(pedido.getId());
        assertEquals(2, pedidoRecuperado.getItens().size());
    }

    public void testDeveriaFazerConsultaPedidoTrazendoClientenComJoinFetch() {
        Pedido pedido = PedidoBuilder.init()
                .cliente(joaoDaSilva)
                .item(10, galaxyS21Plus)
                .item(5, galaxyS21Ultra)
                .build();

        this.em.getTransaction().begin();
        PedidoRepository pedidoDAO = new PedidoDAO(em);
        pedidoDAO.salvar(pedido);
        this.em.flush();

        Pedido pedidoRecuperado = pedidoDAO.buscarPedidoComCliente(pedido.getId());
        this.em.close();

        assertEquals(joaoDaSilva.getNome(), pedidoRecuperado.getCliente().getNome());
    }

    public void testDeveriaLancarExcecaoDeLazyInitializationPorNaoFaze() {
        Pedido pedido = PedidoBuilder.init()
                .cliente(joaoDaSilva)
                .item(10, galaxyS21Plus)
                .item(5, galaxyS21Ultra)
                .build();

        this.em.getTransaction().begin();
        PedidoRepository pedidoDAO = new PedidoDAO(em);
        pedidoDAO.salvar(pedido);
        this.em.flush();

        Pedido pedidoRecuperado = pedidoDAO.buscarPorId(pedido.getId());
        this.em.close();

        assertEquals(joaoDaSilva.getNome(), pedidoRecuperado.getCliente().getNome());
    }

}
