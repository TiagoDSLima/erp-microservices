package com.tiagolima.erp.pedidos.dto;

import java.time.LocalDateTime;

public record ErrorResponse(String campo, String messagem, LocalDateTime dataHora) {
}
