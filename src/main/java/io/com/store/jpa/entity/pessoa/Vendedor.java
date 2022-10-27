package io.com.store.jpa.entity.pessoa;

import io.com.store.jpa.entity.DadosPessoais;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("VDD")
public class Vendedor extends Cliente {

    private String matricula;

    public Vendedor() {
        super();
    }

    public Vendedor(String matricula, DadosPessoais dadosPessoais) {
        super(dadosPessoais);
        this.matricula = matricula;
    }

    public String getMatricula() {
        return matricula;
    }
}
