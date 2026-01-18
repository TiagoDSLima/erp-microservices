package com.tiagolima.erp.pedidos.publisher.representation;

import com.tiagolima.erp.pedidos.enums.StatusPedido;

import java.math.BigDecimal;
import java.util.List;

public record DetalhePedidoRepresentation(
        Long codigo,
        Long codigoCliente,
        String nome,
        String cpf,
        String logradouro,
        String numero,
        String bairro,
        String email,
        String telefone,
        String dataPedido,
        BigDecimal total,
        StatusPedido statusPedido,
        List<DetalheItemPedidoRepresentation> itens) {
}
