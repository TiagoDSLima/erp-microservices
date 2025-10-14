package com.tiagolima.erp.produtos.service;

import com.tiagolima.erp.produtos.model.Produto;
import com.tiagolima.erp.produtos.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public Produto salvar(Produto produto){
        return produtoRepository.save(produto);
    }

    public Optional<Produto> consultarPorCodigo(Long codigo){
        return produtoRepository.findById(codigo);
    }
}
