package com.tiagolima.erp.pedidos.controller;

import com.tiagolima.erp.pedidos.dto.RecebimentoCallBackPagamentoDto;
import com.tiagolima.erp.pedidos.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pedidos/callback-pagamentos")
@RequiredArgsConstructor
public class RecebimentoCallbackPagamentoController {

    private final PedidoService pedidoService;
    @Value("${erpmicroservices.pedido.webhook.api-key}")
    private String apiKeyConfigurada;

    @PostMapping
    public ResponseEntity<Object> atualizarStatusPagamento(
            @RequestBody RecebimentoCallBackPagamentoDto body,
            @RequestHeader(required = true, name = "apiKey") String apiKey){

        ResponseEntity<Object> erro = validaApiKey(apiKey);

        if(erro != null){
            return erro;
        }

        pedidoService.atualizarStatusPagamento(body.codigoPedido(), body.chavePagamento(), body.status(), body.observacoes());
        return ResponseEntity.ok().build();
    }

    private ResponseEntity<Object> validaApiKey(String apiKey) {
        if (apiKey == null || apiKey.isBlank()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("API Key não informada");
        }

        if (!apiKeyConfigurada.equals(apiKey)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("API Key inválida");
        }

        return null;
    }

}
