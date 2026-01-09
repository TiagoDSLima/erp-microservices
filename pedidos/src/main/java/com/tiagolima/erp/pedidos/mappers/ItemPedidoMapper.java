package com.tiagolima.erp.pedidos.mappers;

import com.tiagolima.erp.pedidos.dto.ItemPedidoDto;
import com.tiagolima.erp.pedidos.model.ItemPedido;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ItemPedidoMapper {

    ItemPedido map(ItemPedidoDto dto);
}
