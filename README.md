# 📦 Projeto de Microsserviços – Sistema de Pedidos

## 📖 Visão Geral

Este projeto implementa um **sistema de pedidos baseado em arquitetura de microsserviços**, com comunicação assíncrona via **Apache Kafka**, armazenamento de arquivos no **MinIO** e integração com serviços externos via webhook.

O objetivo principal do projeto é implementar um **sistema de pedidos robusto baseado em microsserviços**, com separação clara de responsabilidades, comunicação orientada a eventos e consistência eventual.

> ⚠️ **Status do projeto**: em fase de finalização.

---

## 🧩 Microsserviços

### 👤 Clientes

Responsável pelo cadastro e consulta de clientes.

* Fornece dados de clientes para o serviço de pedidos

### 📦 Produtos

Responsável pelo cadastro e consulta de produtos.

* Fornece informações de produtos para o serviço de pedidos

### 🧰 Serviços

Serviço auxiliar que **não executa lógica de negócio**.

* Responsável apenas por **organizar e armazenar os containers Docker** do projeto
* Centraliza a infraestrutura necessária para execução do ambiente

Responsável pelo cadastro e consulta de produtos.

* Fornece informações de produtos para o serviço de pedidos

### 🛒 Pedidos (Núcleo da aplicação)

Serviço central do sistema.

* Criação e gerenciamento de pedidos
* Integração com clientes e produtos
* Atualização de status do pedido (CRIADO, PAGO, FATURADO)
* Consumo de eventos de faturamento e logística

### 💰 Faturamento

Responsável pelo processo de faturamento.

* Consome eventos de pedidos pagos
* Gera nota fiscal em PDF
* Armazena a nota fiscal no MinIO
* Publica evento de pedido faturado no Kafka

### 🚚 Logística

Responsável pelo processo logístico.

* Consome eventos de pedidos faturados
* Gera código de rastreamento
* Publica evento de rastreamento no Kafka

---

## 🔄 Fluxo do Sistema

1. O **serviço de pedidos** cria um novo pedido
2. O pedido é enviado para o serviço de pagamento
3. O banco retorna o status do pagamento via **webhook**
4. O serviço de pedidos atualiza o pedido para **PAGO**
5. O pagamento é publicado no **Kafka**
6. O **serviço de faturamento** consome o evento de pagamento
7. A nota fiscal é gerada em PDF
8. A nota fiscal é armazenada no **MinIO**
9. O serviço de faturamento publica o evento de **pedido faturado** no Kafka
10. Dois serviços consomem este evento:

    * **Pedidos**: atualiza o status para FATURADO e salva a URL da nota fiscal
    * **Logística**: gera o código de rastreamento
11. O serviço de logística publica o código de rastreamento no Kafka
12. O serviço de pedidos consome o evento e salva o código de rastreamento

---

## 🧠 Arquitetura

* Arquitetura de **microsserviços desacoplados**
* Comunicação assíncrona via **Apache Kafka**
* Persistência independente por serviço
* Consistência eventual
* Armazenamento de arquivos com **MinIO (S3 compatible)**

---

## 🛠️ Tecnologias Utilizadas

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)&nbsp;
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)&nbsp;
![Kafka](https://img.shields.io/badge/Apache_Kafka-231F20?style=for-the-badge&logo=apache-kafka&logoColor=white)&nbsp;
![Docker](https://img.shields.io/badge/Docker-2CA5E0?style=for-the-badge&logo=docker&logoColor=white)&nbsp;
![JUnit](https://img.shields.io/badge/junit-%23E33332?logo=junit5&logoColor=white)&nbsp;
![MinIO](https://img.shields.io/badge/MinIO-S3%20Compatible-red?style=for-the-badge&logo=amazonaws&logoColor=white)&nbsp;
![PostgreSQL](https://img.shields.io/badge/postgresql-4169e1?style=for-the-badge&logo=postgresql&logoColor=white)&nbsp;

---

## 🚀 Como Executar o Projeto

As configurações de infraestrutura do projeto estão organizadas em **docker-compose.yml**, separados por responsabilidade:

* `broker/` → Apache Kafka, Kafka, Broker
* `database/` → Bancos de dados dos serviços (PostgreSQL)
* `bucket/` → MinIO

Para executar o ambiente, basta acessar cada pasta e subir os containers:

```bash
docker compose up -d
```

---

## 📌 Observações

* Comunicação entre serviços orientada a eventos

* Serviços desacoplados e escaláveis

* Persistência independente por microsserviço

* Ideal para estudo de microsserviços, eventos e integração distribuída

---
