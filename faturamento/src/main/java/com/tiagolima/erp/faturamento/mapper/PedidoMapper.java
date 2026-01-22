package com.tiagolima.erp.faturamento.mapper;

import com.tiagolima.erp.faturamento.model.Cliente;
import com.tiagolima.erp.faturamento.model.ItemPedido;
import com.tiagolima.erp.faturamento.model.Pedido;
import com.tiagolima.erp.faturamento.subscriber.representation.DetalheItemPedidoRepresentation;
import com.tiagolima.erp.faturamento.subscriber.representation.DetalhePedidoRepresentation;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PedidoMapper {

    public Pedido map(DetalhePedidoRepresentation representation) {
        Cliente cliente = new Cliente(
                representation.nome(), representation.cpf(), representation.logradouro(),
                representation.numero(), representation.bairro(), representation.email(),
                representation.telefone()
        );
        List<ItemPedido> itens = representation.itens().stream().map(this::mapItem).toList();

        return new Pedido(representation.codigo(), cliente, representation.dataPedido(), representation.total(), itens);
    }

    private ItemPedido mapItem(DetalheItemPedidoRepresentation representation) {
        return new ItemPedido(representation.codigoProduto(), representation.nomeProduto(), representation.valorUnitario(), representation.quantidade());
    }
}
