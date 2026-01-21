package com.tiagolima.erp.pedidos.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiagolima.erp.pedidos.mappers.DetalhePedidoMapper;
import com.tiagolima.erp.pedidos.model.Pedido;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class PagamentoPublisher {

    private final DetalhePedidoMapper detalhePedidoMapper;
    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;
    @Value("${erpmicroservices.config.kafka.topics.pedidos-pagos}")
    private String topico;

    public void publicar(Pedido pedido) {
        log.info("Publicando pedido pago {}", pedido.getCodigo());

        try{
            var representation = detalhePedidoMapper.map(pedido);
            var json = objectMapper.writeValueAsString(representation);
            kafkaTemplate.send(topico, "dados-pedido", json);
        } catch (JsonProcessingException e) {
            log.error("Erro ao processar o JSON ", e);
        } catch (RuntimeException e) {
            log.error("Erro ao publicar no tópico de pedidos ", e);
        }
    }

}
