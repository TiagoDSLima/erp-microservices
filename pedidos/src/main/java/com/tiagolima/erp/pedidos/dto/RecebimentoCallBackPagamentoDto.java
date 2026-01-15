package com.tiagolima.erp.pedidos.dto;

public record RecebimentoCallBackPagamentoDto(Long codigoPedido, String chavePagamento, boolean status, String observacoes) {
}
