package com.tiagolima.erp.faturamento.service;

import com.tiagolima.erp.faturamento.model.Pedido;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class GeradorNotaFiscalService {

    public void gerarNotaFiscal(Pedido pedido) {
        log.info("Gerando nota fiscal para o pedido {}", pedido.codigo());
    }
}
