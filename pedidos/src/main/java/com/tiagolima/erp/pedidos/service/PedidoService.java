package com.tiagolima.erp.pedidos.service;

import com.tiagolima.erp.pedidos.client.ServicoBancarioClient;
import com.tiagolima.erp.pedidos.dto.NovoPedidoDto;
import com.tiagolima.erp.pedidos.mappers.PedidoMapper;
import com.tiagolima.erp.pedidos.model.Pedido;
import com.tiagolima.erp.pedidos.repository.ItemPedidoRepository;
import com.tiagolima.erp.pedidos.repository.PedidoRepository;
import com.tiagolima.erp.pedidos.validator.PedidoValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ItemPedidoRepository itemPedidoRepository;
    private final PedidoValidator pedidoValidator;
    private final PedidoMapper pedidoMapper;
    private final ServicoBancarioClient servicoBancarioClient;

    @Transactional
    public Pedido criarPedido(NovoPedidoDto novoPedidoDto) {
        var pedido = pedidoMapper.map(novoPedidoDto);
        pedidoValidator.validar(pedido);
        realizarPersistencia(pedido);
        geraChavePagamento(pedido);
        return pedido;
    }

    private void realizarPersistencia(Pedido pedido) {
        pedidoRepository.save(pedido);
        itemPedidoRepository.saveAll(pedido.getItens());
    }

    public void geraChavePagamento(Pedido pedido) {
        pedido.setChavePagamento(servicoBancarioClient.solicitarPagamento(pedido));
    }

}
