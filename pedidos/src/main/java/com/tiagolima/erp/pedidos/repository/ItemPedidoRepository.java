package com.tiagolima.erp.pedidos.repository;
import com.tiagolima.erp.pedidos.model.ItemPedido;
import com.tiagolima.erp.pedidos.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {
    List<ItemPedido> findByPedido(Pedido pedido);
}
