package io.com.store.jpa.entity.categoria;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class CategoriaID implements Serializable {

    @Column(name = "desc_nome")
    private String nome;

    @Column(name = "desc_tipo_categoria")
    private String tipo;

    public CategoriaID() {
        super();
    }

    public CategoriaID(String nome, String tipo) {
        this.nome = nome;
        this.tipo = tipo;
    }

    public String getNome() {
        return nome;
    }

    public String getTipo() {
        return tipo;
    }
}
