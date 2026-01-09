package com.tiagolima.erp.pedidos.dto;

import java.math.BigDecimal;

public record ItemPedidoDto(Long codigoProduto, Integer quantidade, BigDecimal valorUnitario) {
}
