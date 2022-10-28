package io.com.store.jpa.dao;

import io.com.store.jpa.builder.ClienteBuilder;
import io.com.store.jpa.dao.repository.ClienteRepository;
import io.com.store.jpa.dao.repository.PedidoRepository;
import io.com.store.jpa.dao.util.JPAUtil;
import io.com.store.jpa.entity.pessoa.Cliente;
import io.com.store.jpa.entity.pessoa.DadosPessoais;
import io.com.store.jpa.entity.pessoa.Vendedor;
import junit.framework.TestCase;

import javax.persistence.EntityManager;
import java.util.List;

public class ClienteDAOTest extends TestCase {

    private EntityManager em;
    private Cliente joaoDaSilva;

    @Override
    public void setUp() {
        this.em = JPAUtil.getEntityManager();
        this.em.getTransaction().begin();
        excluirConteudoBancoDeDados();
    }

    @Override
    protected void tearDown() throws Exception {
        this.em.getTransaction().commit();
    }

    private void excluirConteudoBancoDeDados() {
        excluirTodosPedidos();
        excluirTodosClientes();
    }

    private void excluirTodosClientes() {
        ClienteRepository clienteRepository = new ClienteDAO(em);
        clienteRepository.buscarTodos().forEach(cliente -> clienteRepository.excluir(cliente));
    }

    private void excluirTodosPedidos() {
        PedidoRepository pedidoRepository = new PedidoDAO(em);
        pedidoRepository.buscarTodos().forEach(pedido -> pedidoRepository.excluir(pedido));
    }

    private void criarClientenJoaoDaSilvaPadrao() {
        joaoDaSilva = ClienteBuilder
                .init()
                .nome("João da Silva")
                .cpf("55412287450")
                .persisted(this.em)
                .build();
    }

    public void testDeveriaBuscarClientesPorNome() {

        ClienteRepository clienteRepository = new ClienteDAO(em);
        criarClientenJoaoDaSilvaPadrao();

        List<Cliente> clientes = clienteRepository.buscarClientes(joaoDaSilva.getNome(), null);

        assertEquals(1, clientes.size());
        clientes.forEach(cliente -> assertEquals(joaoDaSilva.getNome(), cliente.getNome()));
    }

    public void testDeveriaBuscarClientesCriteriaPorCPF() {

        criarClientenJoaoDaSilvaPadrao();

        ClienteRepository clienteRepository = new ClienteDAO(em);
        List<Cliente> clientes = clienteRepository.buscarClientesCriteria(null, joaoDaSilva.getCpf());

        assertEquals(1, clientes.size());
        clientes.forEach(cliente -> assertEquals(joaoDaSilva.getNome(), cliente.getNome()));
    }

    public void testDeveriaBuscarClientesPorCPF() {

        criarClientenJoaoDaSilvaPadrao();

        ClienteRepository clienteRepository = new ClienteDAO(em);
        List<Cliente> clientes = clienteRepository.buscarClientes(null, joaoDaSilva.getCpf());

        assertEquals(1, clientes.size());
        clientes.forEach(cliente -> assertEquals(joaoDaSilva.getNome(), cliente.getNome()));
    }

    public void testDeveriaBuscarTodosClientes() {

        criarClientenJoaoDaSilvaPadrao();

        ClienteBuilder
                .init()
                .nome("Marcin Costa")
                .cpf("05874412036")
                .persisted(em)
                .build();

        ClienteRepository clienteRepository = new ClienteDAO(em);
        List<Cliente> clientes = clienteRepository.buscarClientes(null, null);

        assertEquals(2, clientes.size());
        clientes.forEach(cliente -> assertNotNull(cliente.getNome()));
    }

    public void testDeveriaBuscarClienteCriteriaPorNome() {

        criarClientenJoaoDaSilvaPadrao();

        ClienteRepository clienteRepository = new ClienteDAO(em);
        List<Cliente> clientes = clienteRepository.buscarClientesCriteria(joaoDaSilva.getNome(), null);

        assertEquals(1, clientes.size());
        clientes.forEach(cliente -> assertEquals(joaoDaSilva.getNome(), cliente.getNome()));
    }

    public void testDeveriaBuscarTodosClientesCriteria() {

        criarClientenJoaoDaSilvaPadrao();

        ClienteBuilder
                .init()
                .nome("Marcin Costa")
                .cpf("05874412036")
                .persisted(em)
                .build();


        ClienteRepository clienteRepository = new ClienteDAO(em);
        List<Cliente> clientes = clienteRepository.buscarClientesCriteria(null, null);

        assertEquals(2, clientes.size());
        clientes.forEach(cliente -> assertNotNull(cliente.getNome()));
    }

    public void testDeveriaSalvarVendedor() {

        Vendedor vendedor = new Vendedor("MT122880800", new DadosPessoais("Marcin Costa", "05874412036"));

        ClienteRepository clienteRepository = new ClienteDAO(em);
        clienteRepository.salvar(vendedor);
        List<Cliente> clientes = clienteRepository.buscarClientes("Marcin Costa", "05874412036");

        assertEquals(1, clientes.size());
        clientes.forEach(cliente -> assertNotNull(cliente.getNome()));
    }


}
