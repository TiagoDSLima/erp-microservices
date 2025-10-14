CREATE DATABASE erpprodutos;

CREATE TABLE produtos(
       codigo SERIAL NOT NULL PRIMARY KEY,
       nome VARCHAR(100) NOT NULL,
      valor_unitario DECIMAL(16, 2) NOT NULL
);


CREATE DATABASE erpclientes;

CREATE TABLE clientes(
        codigo SERIAL NOT NULL PRIMARY KEY,
        nome VARCHAR(150) NOT NULL,
        cpf VARCHAR(11) NOT NULL,
        logradouro VARCHAR(100) NOT NULL,
        numero VARCHAR(10),
        bairro VARCHAR (100),
        email VARCHAR(150),
        telefone VARCHAR(20)
);

CREATE DATABASE erppedidos;

CREATE TABLE pedidos(
        codigo SERIAL NOT NULL PRIMARY KEY,
        codigo_cliente BIGINT NOT NULL,
        data_pedido TIMESTAMP NOT NULL DEFAULT NOW(),
        chave_pagamento TEXT,
        observacoes TEXT,
        status VARCHAR(20) CHECK (status in ('REALIZADO', 'PAGO', 'FATURADO', 'ENVIADO', 'ERRO_PAGAMENTO', 'PREPARANDO_ENVIO')),
        total DECIMAL (16, 2) NOT NULL,
        codigo_rastreio VARCHAR(255),
        url_nf TEXT
);

CREATE TABLE item_pedido(
        codigo SERIAL NOT NULL PRIMARY KEY,
        codigo_pedido BIGINT NOT NULL REFERENCES pedidos(codigo),
        codigo_produto BIGINT NOT NULL,
        quantidade DECIMAL(16, 2) NOT NULL,
        valor_unitario DECIMAL(16, 2) NOT NULL
);