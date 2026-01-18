package com.tiagolima.erp.pedidos.controller;

import com.tiagolima.erp.pedidos.dto.NovoPagamentoDto;
import com.tiagolima.erp.pedidos.dto.NovoPedidoDto;
import com.tiagolima.erp.pedidos.mappers.DetalhePedidoMapper;
import com.tiagolima.erp.pedidos.publisher.representation.DetalhePedidoRepresentation;
import com.tiagolima.erp.pedidos.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    private final DetalhePedidoMapper detalhePedidoMapper;

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

    @GetMapping("{codigo}")
    public ResponseEntity<DetalhePedidoRepresentation> obterDetalhesPedido(@PathVariable Long codigo){
        return pedidoService.carregarDadosCompletosPedido(codigo)
                .map(detalhePedidoMapper::map)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}