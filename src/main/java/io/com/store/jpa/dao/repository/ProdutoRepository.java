package io.com.store.jpa.dao.repository;

import io.com.store.jpa.entity.Produto;

import java.util.List;

public interface ProdutoRepository extends BaseRepository<Produto> {
    Produto buscarPorId(long l);
    List<Produto> buscarTodos();

    List<Produto> buscarPorNome(String nome);

    List<Produto> buscarPorNomeCategoria(String nome);
}

