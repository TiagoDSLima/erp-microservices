package com.tiagolima.erp.pedidos.validator;

import com.tiagolima.erp.pedidos.client.ClientesClient;
import com.tiagolima.erp.pedidos.client.ProdutosClient;
import com.tiagolima.erp.pedidos.client.representation.ClienteRepresentation;
import com.tiagolima.erp.pedidos.client.representation.ProdutoRepresentation;
import com.tiagolima.erp.pedidos.exception.ValidationException;
import com.tiagolima.erp.pedidos.model.ItemPedido;
import com.tiagolima.erp.pedidos.model.Pedido;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PedidoValidator {

    private final ProdutosClient produtosClient;
    private final ClientesClient clientesClient;

    public void validar(Pedido pedido) {
        Long codigoCliente = pedido.getCodigoCliente();
        validarCliente(codigoCliente);
        pedido.getItens().forEach(this::validarItem);
    }

    private void validarCliente(Long codigoCliente) {
        try {
            var response = clientesClient.obterDados(codigoCliente);
            ClienteRepresentation cliente = response.getBody();
            log.info("cliente = {}", cliente);
        } catch(FeignException.NotFound e) {
            var message = String.format("Cliente de código %d não encontrado", codigoCliente);
            throw new ValidationException("codigoCliente", message);
        }
    }

    private void validarItem(ItemPedido itemPedido) {
        try {
            var response = produtosClient.obterDados(itemPedido.getCodigo());
            ProdutoRepresentation produto = response.getBody();
            log.info("produto = {}", produto);
        } catch(FeignException.NotFound e) {
            var message = String.format("Produto de código %d não encontrado", itemPedido.getCodigoProduto());
            throw new ValidationException("codigoProduto", message);
        }
    }
}
