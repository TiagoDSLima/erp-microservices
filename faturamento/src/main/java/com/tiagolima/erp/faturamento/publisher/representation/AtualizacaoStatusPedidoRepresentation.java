package com.tiagolima.erp.faturamento.publisher.representation;

import com.tiagolima.erp.faturamento.enums.StatusPedido;

public record AtualizacaoStatusPedidoRepresentation(Long codigoPedido, StatusPedido status, String urlNotaFiscal) {
}
