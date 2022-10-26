package io.com.store.jpa.dao.repository;

import io.com.store.jpa.entity.Pedido;
import io.com.store.jpa.vo.RelatorioVendasVO;

import java.util.List;

public interface PedidoRepository extends BaseRepository<Pedido> {
    Pedido buscarPorId(long l);
    List<Pedido> buscarTodos();
    List<RelatorioVendasVO> relatorioDeProdutoQuantidadeEUltimaVenda();
}

