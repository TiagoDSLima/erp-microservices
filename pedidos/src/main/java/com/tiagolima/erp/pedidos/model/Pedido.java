package com.tiagolima.erp.pedidos.model;

import com.tiagolima.erp.pedidos.client.representation.ClienteRepresentation;
import com.tiagolima.erp.pedidos.enums.StatusPedido;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "pedidos")
@Getter
@Setter
@NoArgsConstructor
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long codigo;

    @Column(name = "codigo_cliente", nullable = false)
    private Long codigoCliente;

    @Column(name = "data_pedido", nullable = false)
    private LocalDateTime dataPedido;

    @Column(name = "total", precision = 16, scale = 2)
    private BigDecimal total;

    @Column(name = "chave_pagamento")
    private String chavePagamento;

    @Column(name = "observacoes")
    private String observacoes;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private StatusPedido status;

    @Column(name = "codigo_rastreio")
    private String codigoRastreio;

    @Column(name = "url_nf")
    private String urlNf;

    @Transient
    private DadosPagamento dadosPagamento;

    @Transient
    private ClienteRepresentation dadosCliente;

    @OneToMany(mappedBy = "pedido")
    private List<ItemPedido> itens;
}
