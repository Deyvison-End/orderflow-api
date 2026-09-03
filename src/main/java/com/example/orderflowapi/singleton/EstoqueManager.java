package com.example.orderflowapi.singleton;

import com.example.orderflowapi.exception.EstoqueInsuficienteException;
import com.example.orderflowapi.model.Produto;
import org.springframework.stereotype.Component;


@Component
public class EstoqueManager {

    public boolean possuiEstoque(Produto produto, Integer quantidade) {
        return produto.getQuantidadeEstoque() >= quantidade;
    }

    public void baixarEstoque(Produto produto, Integer quantidade) {
        if (!possuiEstoque(produto, quantidade)) {
            throw new EstoqueInsuficienteException(
                    "Estoque insuficiente para o pedido " + produto.getNome()
            );
        }

        produto.setQuantidadeEstoque(
                produto.getQuantidadeEstoque() - quantidade
        );
    }
        public void aumentarEstoque(Produto produto, Integer quantidade){

            Integer estoqueAtual = produto.getQuantidadeEstoque();

            produto.setQuantidadeEstoque(
                    estoqueAtual + quantidade
            );
        }
    }

