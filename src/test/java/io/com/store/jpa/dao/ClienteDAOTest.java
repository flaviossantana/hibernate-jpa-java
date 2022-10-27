package io.com.store.jpa.dao;

import io.com.store.jpa.builder.ClienteBuilder;
import io.com.store.jpa.dao.repository.ClienteRepository;
import io.com.store.jpa.dao.util.JPAUtil;
import io.com.store.jpa.entity.Cliente;
import junit.framework.TestCase;

import javax.persistence.EntityManager;
import java.util.List;

public class ClienteDAOTest extends TestCase {

    public void testDeveriaBuscarClientesPorNome() {

        EntityManager entityManager = JPAUtil.getEntityManager();
        entityManager.getTransaction().begin();

        Cliente joaoDaSilva = ClienteBuilder
                .init()
                .nome("João da Silva")
                .cpf("00784452109")
                .persisted(entityManager)
                .build();

        entityManager.flush();

        ClienteRepository clienteRepository = new ClienteDAO(entityManager);
        List<Cliente> clientes = clienteRepository.buscarClientes(joaoDaSilva.getNome(), null);

        assertEquals(1, clientes.size());
        clientes.forEach(cliente -> assertEquals(joaoDaSilva.getNome(), cliente.getNome()));

    }

    public void testDeveriaBuscarClientesPorCPF() {

        EntityManager entityManager = JPAUtil.getEntityManager();
        entityManager.getTransaction().begin();

        Cliente joaoDaSilva = ClienteBuilder
                .init()
                .nome("João da Silva")
                .cpf("00784452109")
                .persisted(entityManager)
                .build();

        entityManager.flush();

        ClienteRepository clienteRepository = new ClienteDAO(entityManager);
        List<Cliente> clientes = clienteRepository.buscarClientes(null, joaoDaSilva.getCpf());

        assertEquals(1, clientes.size());
        clientes.forEach(cliente -> assertEquals(joaoDaSilva.getNome(), cliente.getNome()));
    }

    public void testDeveriaBuscarTodosClientes() {

        EntityManager entityManager = JPAUtil.getEntityManager();
        entityManager.getTransaction().begin();

        ClienteBuilder
                .init()
                .nome("João da Silva")
                .cpf("00784452109")
                .persisted(entityManager)
                .build();

        ClienteBuilder
                .init()
                .nome("Marcin Costa")
                .cpf("05874412036")
                .persisted(entityManager)
                .build();

        entityManager.flush();

        ClienteRepository clienteRepository = new ClienteDAO(entityManager);
        List<Cliente> clientes = clienteRepository.buscarClientes(null, null);

        assertEquals(2, clientes.size());
        clientes.forEach(cliente -> assertNotNull(cliente.getNome()));
    }

}
