package io.com.store.jpa.dao.repository;

public interface BaseRepository<T> {
    void salvar(T produto);
    void atualizar(T produto);
    void excluir(T produto);
}
