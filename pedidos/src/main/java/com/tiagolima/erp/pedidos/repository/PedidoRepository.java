package com.tiagolima.erp.pedidos.repository;


import com.tiagolima.erp.pedidos.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
}
