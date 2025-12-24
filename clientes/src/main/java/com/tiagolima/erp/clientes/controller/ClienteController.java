package com.tiagolima.erp.clientes.controller;

import com.tiagolima.erp.clientes.model.Cliente;
import com.tiagolima.erp.clientes.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @PostMapping
    public ResponseEntity<Cliente> save(@RequestBody Cliente cliente) {
        clienteService.salvar(cliente);
        return ResponseEntity.ok(cliente);
    }

    @GetMapping("{codigo}")
    public ResponseEntity<Cliente> consultarProduto(@PathVariable("codigo") Long codigo){
        return clienteService.consultarPorCodigo(codigo)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
