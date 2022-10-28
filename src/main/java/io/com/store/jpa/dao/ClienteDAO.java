package io.com.store.jpa.dao;

import io.com.store.jpa.dao.repository.ClienteRepository;
import io.com.store.jpa.entity.pessoa.Cliente;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
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
            jpql += "AND c.dadosPessoais.nome = :nome ";
        }
        if (cpf != null) {
            jpql += " AND c.dadosPessoais.cpf = :cpf ";
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

    @Override
    public List<Cliente> buscarClientesCriteria(String nome, String cpf) {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Cliente> query = builder.createQuery(Cliente.class);
        Root<Cliente> from = query.from(Cliente.class);
        Path<Object> dadosPessoais = from.get("dadosPessoais");

        Predicate filtro = builder.and();

        if (nome != null && !nome.trim().isEmpty()) {
            filtro = builder.and(filtro, builder.equal(dadosPessoais.get("nome"), nome));
        }
        if (cpf != null) {
            filtro = builder.and(filtro, builder.equal(dadosPessoais.get("cpf"), cpf));
        }

        query.where(filtro);

        return entityManager.createQuery(query).getResultList();

    }


}
