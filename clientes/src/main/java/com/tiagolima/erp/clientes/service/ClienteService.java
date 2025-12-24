package com.tiagolima.erp.clientes.service;

import com.tiagolima.erp.clientes.model.Cliente;
import com.tiagolima.erp.clientes.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public Cliente salvar(Cliente cliente){
        return clienteRepository.save(cliente);
    }

    public Optional<Cliente> consultarPorCodigo(Long codigo){
        return clienteRepository.findById(codigo);
    }
}
