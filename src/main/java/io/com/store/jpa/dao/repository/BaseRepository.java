package io.com.store.jpa.dao.repository;

import io.com.store.jpa.entity.Produto;

public interface BaseRepository<T> {
    void salvar(T produto);
    void atualizar(T produto);
    void excluir(T produto);
}
