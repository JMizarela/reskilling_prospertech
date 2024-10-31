package com.example.demo;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import jakarta.validation.Validator;
import jakarta.validation.ConstraintViolation;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProdutoTests {

    private static Validator validator;

    private static String nome = "Produto";
    private static Double preco = 10.0;
    private static String descricao = "Descrição";
    private static Integer quantidadeEmEstoque = 100;

    @BeforeAll
    static void setUp() {
        validator = jakarta.validation.Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void whenIdIsNull_thenValidationSucceeds() {
        Produto produto = new Produto(null, nome, preco, descricao, quantidadeEmEstoque);

        Set<ConstraintViolation<Produto>> violations = validator.validate(produto);
        assertFalse(violations.stream().anyMatch(v -> v.getMessage().equals("Id do produto é obrigatório")));
    }

    @Test
    void whenNomeIsNull_thenValidationFails() {
        Produto produto = new Produto(null, null, preco, descricao, quantidadeEmEstoque);

        Set<ConstraintViolation<Produto>> violations = validator.validate(produto);
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Nome do produto é obrigatório")));
    }

    @Test
    void whenNomeIsTooShort_thenValidationFails() {
        Produto produto = new Produto(null, "A", preco, descricao, quantidadeEmEstoque);

        Set<ConstraintViolation<Produto>> violations = validator.validate(produto);
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("O nome do produto deve ter no mínimo 2 caracteres")));
    }

    @Test
    void whenPrecoIsNull_thenValidationFails() {
        Produto produto = new Produto(null, nome, null, descricao, quantidadeEmEstoque);

        Set<ConstraintViolation<Produto>> violations = validator.validate(produto);
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Preço do produto é obrigatório")));
    }

    @Test
    void whenPrecoIsNotPositive_thenValidationFails() {
        Produto produto = new Produto(null, nome, -10.0, descricao, quantidadeEmEstoque);

        Set<ConstraintViolation<Produto>> violations = validator.validate(produto);
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("O preço do produto deve ser maior que zero")));
    }

    @Test
    void whenQuantidadeEmEstoqueIsNegative_thenValidationFails() {
        Produto produto = new Produto(null, nome, preco, descricao, -1);

        Set<ConstraintViolation<Produto>> violations = validator.validate(produto);
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("A quantidade em estoque não pode ser negativa")));
    }

    @Test
    void whenAllFieldsAreValid_thenValidationSucceeds() {
        Produto produto = new Produto(null, nome, preco, descricao, quantidadeEmEstoque);

        Set<ConstraintViolation<Produto>> violations = validator.validate(produto);
        assertTrue(violations.isEmpty());
    }
}