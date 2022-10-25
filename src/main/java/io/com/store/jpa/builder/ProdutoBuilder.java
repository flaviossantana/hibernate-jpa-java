package io.com.store.jpa.builder;

import io.com.store.jpa.entity.Categoria;
import io.com.store.jpa.entity.Produto;

import java.math.BigDecimal;

public class ProdutoBuilder {

    private Produto produto = new Produto();

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
        return produto;
    }

}
