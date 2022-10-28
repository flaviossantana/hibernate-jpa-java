package io.com.store.jpa.entity.produto;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "livros")
public class Livro extends Produto {

    private String isbn;
    private String autor;
    private Integer numeroDePaginas;

    public Livro() {
        super();
    }

    public Livro(String isbn, String autor, Integer numeroDePaginas) {
        this.isbn = isbn;
        this.autor = autor;
        this.numeroDePaginas = numeroDePaginas;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getAutor() {
        return autor;
    }

    public Integer getNumeroDePaginas() {
        return numeroDePaginas;
    }
}
