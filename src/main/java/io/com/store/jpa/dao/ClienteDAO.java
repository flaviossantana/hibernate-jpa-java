package io.com.store.jpa.dao;

import io.com.store.jpa.dao.repository.ClienteRepository;
import io.com.store.jpa.entity.Cliente;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class ClienteDAO implements ClienteRepository {

    private EntityManager entityManager;

    public ClienteDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void salvar(Cliente produto) {
        entityManager.persist(produto);
    }

    @Override
    public void atualizar(Cliente produto) {
        entityManager.merge(produto);
    }

    @Override
    public void excluir(Cliente produto) {
        produto = entityManager.merge(produto);
        entityManager.remove(produto);
    }

    @Override
    public Cliente buscarPorId(long id) {
        return entityManager.find(Cliente.class, id);
    }

    @Override
    public List<Cliente> buscarTodos() {
        return entityManager
                .createQuery("SELECT p FROM Cliente p", Cliente.class)
                .getResultList();
    }

    @Override
    public List<Cliente> buscarClientes(String nome, String cpf) {
        String jpql = "SELECT c FROM Cliente c WHERE 0=0 ";

        if (nome != null && !nome.trim().isEmpty()) {
            jpql += "AND c.nome = :nome ";
        }
        if (cpf != null) {
            jpql += " AND c.cpf = :cpf ";
        }

        TypedQuery<Cliente> query = entityManager.createQuery(jpql, Cliente.class);

        if (nome != null && !nome.trim().isEmpty()) {
            query.setParameter("nome", nome);
        }
        if (cpf != null) {
            query.setParameter("cpf", cpf);
        }
        return query.getResultList();
    }
}
