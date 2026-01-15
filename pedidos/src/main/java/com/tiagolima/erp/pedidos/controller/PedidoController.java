package com.tiagolima.erp.pedidos.controller;

import com.tiagolima.erp.pedidos.dto.NovoPagamentoDto;
import com.tiagolima.erp.pedidos.dto.NovoPedidoDto;
import com.tiagolima.erp.pedidos.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;

    @PostMapping
    public ResponseEntity criar(@RequestBody NovoPedidoDto novoPedidoDto) {
        var novoPedido = pedidoService.criarPedido(novoPedidoDto);
        return ResponseEntity.ok(novoPedido.getCodigo());
    }

    @PutMapping
    public ResponseEntity novoPagamento(@RequestBody NovoPagamentoDto novoPagamentoDto) {
        pedidoService.adicionarNovoPagamento(novoPagamentoDto.codigoPedido(),
                novoPagamentoDto.numeroCartao(),
                novoPagamentoDto.validade(),
                novoPagamentoDto.codigoCartao(),
                novoPagamentoDto.nomeTitular(),
                novoPagamentoDto.tipoPagamento());
        return ResponseEntity.noContent().build();
    }


}