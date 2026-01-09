package com.tiagolima.erp.pedidos.repository;
import com.tiagolima.erp.pedidos.model.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {
}
