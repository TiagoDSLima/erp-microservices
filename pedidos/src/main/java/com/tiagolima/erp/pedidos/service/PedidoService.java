package com.tiagolima.erp.pedidos.service;

import com.tiagolima.erp.pedidos.client.ClientesClient;
import com.tiagolima.erp.pedidos.client.ProdutosClient;
import com.tiagolima.erp.pedidos.client.ServicoBancarioClient;
import com.tiagolima.erp.pedidos.client.representation.ClienteRepresentation;
import com.tiagolima.erp.pedidos.dto.NovoPedidoDto;
import com.tiagolima.erp.pedidos.enums.StatusPedido;
import com.tiagolima.erp.pedidos.enums.TipoPagamento;
import com.tiagolima.erp.pedidos.exception.ValidationException;
import com.tiagolima.erp.pedidos.mappers.PedidoMapper;
import com.tiagolima.erp.pedidos.model.DadosPagamento;
import com.tiagolima.erp.pedidos.model.ItemPedido;
import com.tiagolima.erp.pedidos.model.Pedido;
import com.tiagolima.erp.pedidos.repository.ItemPedidoRepository;
import com.tiagolima.erp.pedidos.repository.PedidoRepository;
import com.tiagolima.erp.pedidos.validator.PedidoValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ItemPedidoRepository itemPedidoRepository;
    private final PedidoValidator pedidoValidator;
    private final PedidoMapper pedidoMapper;
    private final ServicoBancarioClient servicoBancarioClient;
    private final ClientesClient clientesClient;
    private final ProdutosClient produtosClient;

    @Transactional
    public Pedido criarPedido(NovoPedidoDto novoPedidoDto) {
        var pedido = pedidoMapper.map(novoPedidoDto);
        pedidoValidator.validar(pedido);
        realizarPersistenciaPedidosEItens(pedido);
        geraChavePagamento(pedido);
        return pedido;
    }

    public void geraChavePagamento(Pedido pedido) {
        pedido.setChavePagamento(servicoBancarioClient.solicitarPagamento(pedido));
    }

    public void atualizarStatusPagamento(Long codigoPedido, String chavePagamento, boolean sucesso, String observacoes) {
        var pedidoEncontrado = buscaPedidoPorCodigoEChavePagamento(codigoPedido, chavePagamento);

        if(pedidoEncontrado.isEmpty()) {
            var msg = String.format("Pedido não encontrado para o código %d e chave de pagamento %s", codigoPedido, chavePagamento);
            log.error(msg);
            if(naoExistePedidoComChavePagamento(chavePagamento)){
                throw new ValidationException("chavePagamento", String.format("Pedido não encontrado para a chave de pagamento %s", chavePagamento));
            } else if(naoExistePedidoComCodigo(codigoPedido)) {
                throw new ValidationException("codigoPedido", String.format("Pedido não encontrado para o código %d", codigoPedido));
            } else {
                throw new ValidationException("chavePagamento/codigoPedido", msg);
            }
        }

        Pedido pedido = pedidoEncontrado.get();

        if(sucesso){
            pedido.setStatus(StatusPedido.PAGO);
            pedido.setObservacoes("Pagamento feito!");
        } else {
            pedido.setStatus(StatusPedido.ERRO_PAGAMENTO);
            pedido.setObservacoes(observacoes);
        }
        realizarPersistenciaPedido(pedido);
    }

    @Transactional
    public void adicionarNovoPagamento(
            Long codigoPedido, String numeroCartao, String validade, String codigoCartao, String nomeTitular, TipoPagamento tipoPagamento){

        var pedidoEncontrado = buscaPedidoPorCodigo(codigoPedido);

        if(pedidoEncontrado.isEmpty()) {
            throw new ValidationException("codigoPedido", String.format("Pedido não encontrado para o código %d", codigoPedido));
        }

        var pedido = pedidoEncontrado.get();
        DadosPagamento dadosPagamento = new DadosPagamento(numeroCartao, validade, codigoCartao, nomeTitular, tipoPagamento);
        pedido.setDadosPagamento(dadosPagamento);
        pedido.setStatus(StatusPedido.REALIZADO);
        pedido.setObservacoes("Novo pagamento realizado, aguardando processamento");

        String novaChavePagamento = servicoBancarioClient.solicitarPagamento(pedido);
        pedido.setChavePagamento(novaChavePagamento);
    }

    public Optional<Pedido> carregarDadosCompletosPedido(Long codigoPedido) {
        Optional<Pedido> pedido = buscaPedidoPorCodigo(codigoPedido);
        pedido.ifPresent(this::carregarDadosCliente);
        pedido.ifPresent(this::carregarItensPedido);
        return pedido;
    }

    private void carregarDadosCliente(Pedido pedido) {
        Long codigoCliente = pedido.getCodigoCliente();
        var response = clientesClient.obterDados(codigoCliente);
        pedido.setDadosCliente(response.getBody());
    }

    private void carregarItensPedido(Pedido pedido) {
        List<ItemPedido> itens = itemPedidoRepository.findByPedido(pedido);
        pedido.setItens(itens);
        pedido.getItens().forEach(this::carregarDadosProduto);
    }

    private void carregarDadosProduto(ItemPedido itemPedido) {
        Long codigoProduto = itemPedido.getCodigoProduto();
        var response = produtosClient.obterDados(codigoProduto);
        itemPedido.setNomeProduto(response.getBody().nome());
    }

    private Optional<Pedido> buscaPedidoPorCodigoEChavePagamento(Long codigoPedido, String chavePagamento) {
        return pedidoRepository.findByCodigoAndChavePagamento(codigoPedido, chavePagamento);
    }

    private boolean naoExistePedidoComChavePagamento(String chavePagamento) {
        return !pedidoRepository.existsByChavePagamento(chavePagamento);
    }

    private boolean naoExistePedidoComCodigo(Long codigoPedido) {
        return !pedidoRepository.existsByCodigo(codigoPedido);
    }

    private void realizarPersistenciaPedidosEItens(Pedido pedido) {
        pedidoRepository.save(pedido);
        itemPedidoRepository.saveAll(pedido.getItens());
    }

    private void realizarPersistenciaPedido(Pedido pedido) {
        pedidoRepository.save(pedido);
    }

    private Optional<Pedido> buscaPedidoPorCodigo(Long codigoPedido) {
        return pedidoRepository.findByCodigo(codigoPedido);
    }
}
