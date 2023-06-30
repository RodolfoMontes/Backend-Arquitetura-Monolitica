package com.montes.montes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.montes.montes.model.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
}
