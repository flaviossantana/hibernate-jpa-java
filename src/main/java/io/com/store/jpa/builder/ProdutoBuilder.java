package io.com.store.jpa.builder;

import io.com.store.jpa.dao.ProdutoDAO;
import io.com.store.jpa.dao.repository.ProdutoRepository;
import io.com.store.jpa.entity.Categoria;
import io.com.store.jpa.entity.Produto;

import javax.persistence.EntityManager;
import java.math.BigDecimal;

public class ProdutoBuilder {

    private Produto produto = new Produto();
    private boolean isPersisted = false;

    private ProdutoRepository produtoRepository;

    private ProdutoBuilder() {
        super();
    }

    public static ProdutoBuilder init() {
        return new ProdutoBuilder();
    }

    public ProdutoBuilder nome(String nome) {
        produto.setNome(nome);
        return this;
    }

    public ProdutoBuilder descricao(String descricao) {
        produto.setDescricao(descricao);
        return this;
    }

    public ProdutoBuilder preco(String valor) {
        produto.setPreco(new BigDecimal(valor));
        return this;
    }

    public ProdutoBuilder categoria(Categoria categoria) {
        produto.setCategoria(categoria);
        return this;
    }

    public ProdutoBuilder nomeCategoria(String nomeCategoria) {
        produto.setCategoria(new Categoria(nomeCategoria));
        return this;
    }

    public Produto build() {
        if(isPersisted) {
            produtoRepository.salvar(produto);
        }

        return produto;
    }

    public ProdutoBuilder persisted(EntityManager em) {
        this.produtoRepository = new ProdutoDAO(em);
        this.isPersisted = true;
        return this;
    }
}
