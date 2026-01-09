package com.tiagolima.erp.pedidos.mappers;

import com.tiagolima.erp.pedidos.dto.DadosPagamentoDto;
import com.tiagolima.erp.pedidos.model.DadosPagamento;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DadosPagamentoMapper {

    DadosPagamento map(DadosPagamentoDto dto);
}
