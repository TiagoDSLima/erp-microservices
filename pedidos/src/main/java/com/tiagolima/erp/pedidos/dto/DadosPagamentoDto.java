package com.tiagolima.erp.pedidos.dto;

import com.tiagolima.erp.pedidos.enums.TipoPagamento;

public record DadosPagamentoDto(String chavePix, String numeroCartao, String validade, String codigoCartao, String nomeTitular, TipoPagamento tipoPagamento) {
}
