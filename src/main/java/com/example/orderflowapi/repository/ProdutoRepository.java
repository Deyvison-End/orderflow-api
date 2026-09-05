package com.example.orderflowapi.repository;

import com.example.orderflowapi.model.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer >,
        JpaSpecificationExecutor<Produto> {

}
