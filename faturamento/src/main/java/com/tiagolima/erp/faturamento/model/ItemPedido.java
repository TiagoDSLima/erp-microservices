package com.tiagolima.erp.faturamento.model;

import java.math.BigDecimal;

public record ItemPedido(Long codigo, String descricao, BigDecimal valorUnitario, Integer quantidade, BigDecimal total) {
}
