package com.tiagolima.erp.faturamento.subscriber;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiagolima.erp.faturamento.mapper.PedidoMapper;
import com.tiagolima.erp.faturamento.model.Pedido;
import com.tiagolima.erp.faturamento.service.GeradorNotaFiscalService;
import com.tiagolima.erp.faturamento.subscriber.representation.DetalhePedidoRepresentation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PedidoPagoSubscriber {

    private final ObjectMapper objectMapper;
    private final GeradorNotaFiscalService geradorNotaFiscalService;
    private final PedidoMapper pedidoMapper;

    @KafkaListener(groupId = "erp-faturamento", topics = "${erpmicroservices.config.kafka.topics.pedidos-pagos}")
    public void listen(String json){
        try{
            log.info("Recebendo pedido para faturamento {}", json);
            var representation = objectMapper.readValue(json, DetalhePedidoRepresentation.class);
            Pedido pedido = pedidoMapper.map(representation);
            geradorNotaFiscalService.gerarNotaFiscal(pedido);
        }catch (Exception e){
            log.error("Erro na consumação de pedidos pagos: ", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
