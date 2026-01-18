package com.tiagolima.erp.pedidos.publisher.representation;

import java.math.BigDecimal;

public record DetalheItemPedidoRepresentation(Long codigoProduto, String nomeProduto, Integer quantidade, BigDecimal valorUnitario) {

    public BigDecimal getTotal() {
        return valorUnitario.multiply(new BigDecimal(quantidade));
    }
}