package com.tiagolima.erp.faturamento.model;

import lombok.AllArgsConstructor;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {

    private Long codigo;
    private Cliente cliente;
    private String dataPedido;
    private BigDecimal totalPedido;
    private List<ItemPedido> itens;
}
