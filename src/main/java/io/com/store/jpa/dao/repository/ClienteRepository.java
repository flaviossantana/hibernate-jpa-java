package io.com.store.jpa.dao.repository;

import io.com.store.jpa.entity.pessoa.Cliente;

import java.util.List;

public interface ClienteRepository extends BaseRepository<Cliente> {
    Cliente buscarPorId(long l);
    List<Cliente> buscarTodos();
    List<Cliente> buscarClientes(String nome, String cpf);

    List<Cliente> buscarClientesCriteria(String nome, String cpf);
}

