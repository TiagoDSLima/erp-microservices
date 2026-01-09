package com.tiagolima.erp.pedidos.mappers;
import com.tiagolima.erp.pedidos.dto.NovoPedidoDto;
import com.tiagolima.erp.pedidos.enums.StatusPedido;
import com.tiagolima.erp.pedidos.model.Pedido;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Mapper(
        componentModel = "spring",
        uses = {
                ItemPedidoMapper.class,
                DadosPagamentoMapper.class
        }
)
public interface PedidoMapper {

    Pedido map(NovoPedidoDto novoPedidoDto);

    @AfterMapping
    default void afterMapping(@MappingTarget Pedido pedido){
        pedido.setStatus(StatusPedido.REALIZADO);
        pedido.setDataPedido(LocalDateTime.now());

        var total = calcularTotal(pedido);
        
        pedido.setTotal(total);

        pedido.getItens().forEach(itemPedido -> itemPedido.setPedido(pedido));
    }

    private static BigDecimal calcularTotal(Pedido pedido) {
        return pedido.getItens().stream().map(itemPedido ->
                itemPedido.getValorUnitario().multiply(BigDecimal.valueOf(itemPedido.getQuantidade()))
        ).reduce(BigDecimal.ZERO, BigDecimal::add).abs();
    }
}
