package io.com.store.jpa.dao.repository;

import io.com.store.jpa.entity.produto.Produto;

import java.math.BigDecimal;
import java.util.List;

public interface ProdutoRepository extends BaseRepository<Produto> {
    Produto buscarPorId(long l);
    List<Produto> buscarTodos();

    List<Produto> buscarPorNome(String nome);

    List<Produto> buscarPorNomeCategoria(String nome);

    BigDecimal buscarPrecoPorNome(String nome);

    BigDecimal buscarPrecoPorNomeNamedQuery(String nome);
}

