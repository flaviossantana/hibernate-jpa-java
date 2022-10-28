package io.com.store.jpa.entity.categoria;

import javax.persistence.*;

@Entity
@Table(name = "categorias")
public class Categoria {


    @EmbeddedId
    private CategoriaID id;

    @Column(name = "stat_ativo")
    private Boolean ativo = true;

    public Categoria() {
        super();
    }

    public Categoria(String nome) {
        this.id = new CategoriaID(nome, "DEFAULT");
    }

    public String getNome() {
        return this.id.getNome();
    }

    public CategoriaID getId() {
        return id;
    }

    public void inativar() {
        this.ativo = false;
    }

    public boolean getAtivo() {
        return this.ativo;
    }
}
