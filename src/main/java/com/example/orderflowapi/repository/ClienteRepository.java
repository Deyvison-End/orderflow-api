package com.example.orderflowapi.repository;

import com.example.orderflowapi.model.Cliente;
import com.example.orderflowapi.model.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer>,
        JpaSpecificationExecutor<Cliente> {

}
