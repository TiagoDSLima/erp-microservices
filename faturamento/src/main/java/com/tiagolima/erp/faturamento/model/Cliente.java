package com.tiagolima.erp.faturamento.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {

    private String nome;
    private String cpf;
    private String logradouro;
    private String numero;
    private String bairro;
    private String email;
    private String telefone;
}