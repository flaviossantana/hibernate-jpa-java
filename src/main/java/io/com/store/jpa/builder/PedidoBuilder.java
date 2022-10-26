package io.com.store.jpa.builder;

import io.com.store.jpa.entity.Cliente;
import io.com.store.jpa.entity.ItemPedido;
import io.com.store.jpa.entity.Pedido;
import io.com.store.jpa.entity.Produto;

public class PedidoBuilder {

    private Pedido pedido;

    private PedidoBuilder() {
        super();
    }

    public static PedidoBuilder init() {
        PedidoBuilder builder = new PedidoBuilder();
        builder.pedido = new Pedido();
        return builder;
    }

    public PedidoBuilder cliente(Cliente cliente) {
        this.pedido.setCliente(cliente);
        return this;
    }

    public PedidoBuilder item(int quantidade, Produto produto) {
        this.pedido.addItem(new ItemPedido(quantidade, produto));
        return this;
    }

    public Pedido build() {
        return this.pedido;
    }
}
