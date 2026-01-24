package com.tiagolima.erp.faturamento.publisher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiagolima.erp.faturamento.enums.StatusPedido;
import com.tiagolima.erp.faturamento.model.Pedido;
import com.tiagolima.erp.faturamento.publisher.representation.AtualizacaoStatusPedidoRepresentation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class FaturamentoPublisher {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Value("${erpmicroservices.config.kafka.topics.pedidos-faturas}")
    private String topico;

    public void publicar(Pedido pedido, String urlNotaFiscal) {
        try {
            var representation = new AtualizacaoStatusPedidoRepresentation(pedido.getCodigo(), StatusPedido.FATURADO, urlNotaFiscal);
            String json = objectMapper.writeValueAsString(representation);
            kafkaTemplate.send(topico, "dados-faturamento", json);
        } catch (Exception e) {
            log.error("Erro ao publicar pedido faturado {}", e.getMessage());
        }
    }
}
