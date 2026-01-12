package com.tiagolima.erp.pedidos.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.tiagolima.erp.pedidos.client")
public class ClientsConfig {
}
