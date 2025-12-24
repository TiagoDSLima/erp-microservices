package com.tiagolima.erp.clientes.repository;

import com.tiagolima.erp.clientes.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
