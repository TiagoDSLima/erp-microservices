package com.tiagolima.erp.pedidos.dto;

import java.util.List;

public record NovoPedidoDto(Long codigoCliente, DadosPagamentoDto dadosPagamento, List<ItemPedidoDto> itens) {
}
