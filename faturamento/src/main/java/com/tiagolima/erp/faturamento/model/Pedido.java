package com.tiagolima.erp.faturamento.model;

import java.math.BigDecimal;
import java.util.List;

public record Pedido(Long codigo, Cliente cliente, String dataPedido, BigDecimal totalPedido, List<ItemPedido> itens) {
}
