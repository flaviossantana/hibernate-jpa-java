package io.com.store.jpa.builder;

import io.com.store.jpa.dao.ClienteDAO;
import io.com.store.jpa.dao.ProdutoDAO;
import io.com.store.jpa.dao.repository.ClienteRepository;
import io.com.store.jpa.dao.util.JPAUtil;
import io.com.store.jpa.entity.Cliente;

import javax.persistence.EntityManager;

public class ClienteBuilder {

    private Cliente cliente;
    private boolean isPersisted = false;
    private ClienteRepository clienteRepository;

    private ClienteBuilder() {
        super();
    }

    public static ClienteBuilder init() {
        ClienteBuilder builder = new ClienteBuilder();
        builder.cliente = new Cliente();
        return builder;
    }

    public ClienteBuilder nome(String nome) {
        this.cliente.setNome(nome);
        return this;
    }

    public ClienteBuilder cpf(String cpf) {
        this.cliente.setCpf(cpf);
        return this;
    }

    public ClienteBuilder persisted(EntityManager em) {
        this.clienteRepository = new ClienteDAO(em);
        this.isPersisted = true;
        return this;
    }

    public Cliente build() {
        if(isPersisted) {
            clienteRepository.salvar(cliente);
        }

        return cliente;
    }

}
