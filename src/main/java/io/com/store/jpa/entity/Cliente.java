package io.com.store.jpa.entity;

import javax.persistence.*;

@Entity
@Table(name = "clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private DadosPessoais dadosPessoais;

//    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY)
//    private List<Pedido> pedidos = new ArrayList<>();

    public Cliente() {
        this.dadosPessoais = new DadosPessoais();
    }

    public Cliente(String nome, String cpf) {
        this.dadosPessoais = new DadosPessoais(nome, cpf);
    }

    public Long getId() {
        return id;
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
