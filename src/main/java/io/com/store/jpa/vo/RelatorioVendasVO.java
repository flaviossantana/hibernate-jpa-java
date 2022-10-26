package io.com.store.jpa.vo;

import java.time.LocalDateTime;

public class RelatorioVendasVO {

    private String nomeProduto;
    private Long quantidade;
    private LocalDateTime ultimaVenda;


    public RelatorioVendasVO(String nomeProduto, Long quantidade, LocalDateTime ultimaVenda) {
        this.nomeProduto = nomeProduto;
        this.quantidade = quantidade;
        this.ultimaVenda = ultimaVenda;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public Long getQuantidade() {
        return quantidade;
    }

    public LocalDateTime getUltimaVenda() {
        return ultimaVenda;
    }


    @Override
    public String toString() {
        return "RelatorioVendasVO{" +
                "nomeProduto='" + nomeProduto + '\'' +
                ", quantidade=" + quantidade +
                ", ultimaVenda=" + ultimaVenda +
                '}';
    }
}
