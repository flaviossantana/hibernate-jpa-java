package io.com.store.jpa.dao.repository;

import io.com.store.jpa.entity.Cliente;
import io.com.store.jpa.entity.Produto;

import java.time.LocalDate;
import java.util.List;

public interface ClienteRepository extends BaseRepository<Cliente> {
    Cliente buscarPorId(long l);
    List<Cliente> buscarTodos();
    List<Cliente> buscarClientes(String nome, String cpf);
}

