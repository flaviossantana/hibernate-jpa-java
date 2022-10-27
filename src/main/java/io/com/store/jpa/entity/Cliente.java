package io.com.store.jpa.entity;

import javax.persistence.*;

@Entity
@Table(name = "clientes")
@DiscriminatorValue("CLT")
@DiscriminatorColumn(name = "tipo_cliente", discriminatorType = DiscriminatorType.STRING)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private DadosPessoais dadosPessoais;

    public Cliente() {
        this.dadosPessoais = new DadosPessoais();
    }

    public Cliente(DadosPessoais dadosPessoais) {
        this.dadosPessoais = dadosPessoais;
    }

    public String getNome() {
        return this.dadosPessoais.getNome();
    }

    public String getCpf() {
        return this.dadosPessoais.getCpf();
    }

    public void setNome(String nome) {
        this.dadosPessoais.setNome(nome);
    }

    public void setCpf(String cpf) {
        this.dadosPessoais.setCpf(cpf);
    }

}
