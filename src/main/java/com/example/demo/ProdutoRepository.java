package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RestController;

@RestController
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
