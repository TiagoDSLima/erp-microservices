package com.tiagolima.erp.pedidos.service;

import com.tiagolima.erp.pedidos.dto.NovoPedidoDto;
import com.tiagolima.erp.pedidos.mappers.PedidoMapper;
import com.tiagolima.erp.pedidos.model.Pedido;
import com.tiagolima.erp.pedidos.repository.ItemPedidoRepository;
import com.tiagolima.erp.pedidos.repository.PedidoRepository;
import com.tiagolima.erp.pedidos.validator.PedidoValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ItemPedidoRepository itemPedidoRepository;
    private final PedidoValidator pedidoValidator;
    private final PedidoMapper pedidoMapper;

    public Pedido criarPedido(NovoPedidoDto novoPedidoDto) {
        var Pedido = pedidoMapper.map(novoPedidoDto);
        pedidoRepository.save(Pedido);
        itemPedidoRepository.saveAll(Pedido.getItens());
        return Pedido;
    }

}
