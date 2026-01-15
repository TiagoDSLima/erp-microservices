package com.tiagolima.erp.pedidos.model;

import com.tiagolima.erp.pedidos.enums.TipoPagamento;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DadosPagamento {

    private String numeroCartao;
    private String validade;
    private String codigoCartao;
    private String nomeTitular;
    private TipoPagamento tipoPagamento;
}
