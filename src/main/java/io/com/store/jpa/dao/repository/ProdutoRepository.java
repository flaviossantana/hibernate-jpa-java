package io.com.store.jpa.dao.repository;

import io.com.store.jpa.entity.Produto;

public interface ProdutoRepository {
    void salvar(Produto produto);
}
