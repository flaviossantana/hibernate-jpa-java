package io.com.store.jpa.entity;

import io.com.store.jpa.builder.ClienteBuilder;
import io.com.store.jpa.builder.PedidoBuilder;
import io.com.store.jpa.builder.ProdutoBuilder;
import io.com.store.jpa.dao.PedidoDAO;
import io.com.store.jpa.dao.repository.PedidoRepository;
import io.com.store.jpa.dao.util.JPAUtil;
import io.com.store.jpa.vo.RelatorioVendasVO;
import junit.framework.TestCase;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

public class PedidoTest extends TestCase {

    private Produto galaxyS21Plus;
    private Produto galaxyS21Ultra;
    private Cliente joaoDaSilva;
    private EntityManager em;

    public void setUp() {
        this.em = JPAUtil.getEntityManager();
        this.em.getTransaction().begin();
        criarProdutos();
        criarCliente();
        this.em.getTransaction().commit();
    }

    protected void tearDown()  {
        em.close();
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

    public void testDeveriaInstanciarPedido() {

        Pedido pedido = PedidoBuilder
                .init()
                .cliente(joaoDaSilva)
                .item(1, galaxyS21Plus)
                .item(2, galaxyS21Ultra)
                .build();

        em.getTransaction().begin();
        PedidoRepository pedidoRepository = new PedidoDAO(em);
        pedidoRepository.salvar(pedido);
        em.getTransaction().commit();
    }

    public void testeDeveriaSomarTotalDeTodasAsVendas(){



        Pedido pedidoSeisMil = PedidoBuilder
                .init()
                .cliente(joaoDaSilva)
                .item(1, galaxyS21Plus)
                .item(2, galaxyS21Ultra)
                .build();

        Pedido pedidoDozeMil = PedidoBuilder
                .init()
                .cliente(joaoDaSilva)
                .item(1, galaxyS21Plus)
                .item(3, galaxyS21Plus)
                .item(2, galaxyS21Ultra)
                .item(4, galaxyS21Ultra)
                .build();

        em.getTransaction().begin();
        PedidoRepository pedidoRepository = new PedidoDAO(em);

        pedidoRepository.buscarTodos().forEach(p -> pedidoRepository.excluir(p));

        pedidoRepository.salvar(pedidoSeisMil);
        pedidoRepository.salvar(pedidoDozeMil);
        em.getTransaction().commit();

        BigDecimal totalVendido = pedidoRepository.valorTotalDeTodasAsVendas();
        assertEquals(new BigDecimal("45000.00"), totalVendido);
    }

    public void testeDeveriaExecutarRelatorioDeProdutoQuantidadeEUltimaVenda(){

        Pedido pedido = PedidoBuilder
                .init()
                .cliente(joaoDaSilva)
                .item(1, galaxyS21Plus)
                .item(2, galaxyS21Ultra)
                .item(10, ProdutoBuilder.init()
                        .nome("GALAXY S22 NOTE")
                        .descricao("SMARTPHONE SAMSUMG GALAXY S22 NOTE 1TB")
                        .preco("7890.99")
                        .nomeCategoria("SMARTPHONES")
                        .persisted(this.em)
                        .build())
                .build();

        em.getTransaction().begin();
        PedidoRepository pedidoRepository = new PedidoDAO(em);
        pedidoRepository.salvar(pedido);
        em.getTransaction().commit();

        List<RelatorioVendasVO> relatorioVendasVOS = pedidoRepository.relatorioDeProdutoQuantidadeEUltimaVenda();

        relatorioVendasVOS.forEach(System.out::println);
    }


}
