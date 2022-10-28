package io.com.store.jpa.dao.repository;

import io.com.store.jpa.entity.categoria.Categoria;
import io.com.store.jpa.entity.categoria.CategoriaID;

public interface CategoriaRepository extends BaseRepository<Categoria> {
    Categoria buscarPorId(CategoriaID id);
}
