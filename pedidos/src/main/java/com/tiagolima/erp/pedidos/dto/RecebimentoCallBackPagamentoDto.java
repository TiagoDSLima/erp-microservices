package com.tiagolima.erp.pedidos.dto;

public record RecebimentoCallBackPagamentoDto(Long codigo, String chavePagamento, boolean status, String observacoes) {
}
