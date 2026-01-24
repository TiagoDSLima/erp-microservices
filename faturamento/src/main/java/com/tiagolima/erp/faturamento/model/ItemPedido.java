package com.tiagolima.erp.faturamento.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemPedido {

    private Long codigo;
    private String descricao;
    private BigDecimal valorUnitario;
    private Integer quantidade;
    private BigDecimal total;
}
