package com.tiagolima.erp.produtos.repository;

import com.tiagolima.erp.produtos.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
