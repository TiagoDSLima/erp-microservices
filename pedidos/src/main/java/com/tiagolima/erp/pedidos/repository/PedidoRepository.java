package com.tiagolima.erp.pedidos.repository;


import com.tiagolima.erp.pedidos.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

    Optional<Pedido> findByCodigoAndChavePagamento(Long codigo, String chavePagamento);

    boolean existsByChavePagamento(String chavePagamento);

    boolean existsByCodigo(Long codigo);

    Optional<Pedido> findByCodigo(Long codigo);
}
