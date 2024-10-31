package com.example.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IntegrationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    private Long produtoId;

    @BeforeEach
    void setup() {
        // Arrange
        String url = "/produtos";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        Produto produto = new Produto(null, "Produto1", 10.0, "Description1", 100);

        HttpEntity<Produto> request = new HttpEntity<>(produto, headers);

        // Act
        ResponseEntity<Produto> response =  restTemplate.exchange(url, HttpMethod.POST, request, Produto.class);

        // Salvar o  auto-generated ID para uso em outros testes
        produtoId = response.getBody().getId();
    }

    @Test
    void quandoCriarProdutoEntaoProdutoCriado() {
        // Arrange
        String url = "/produtos";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        Produto produto = new Produto(null, "Produto2", 20.0, "Description2", 200);

        HttpEntity<Produto> request = new HttpEntity<>(produto, headers);

        // Act
        ResponseEntity<Produto> response = restTemplate.exchange(url, HttpMethod.POST, request, Produto.class);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Produto2", response.getBody().getNome());
    }

    @Test
    void quandoListarTodosProdutosEntaoProdutosListados() {
        // Arrange
        String url = "/produtos";

        // Act
        ResponseEntity<Produto[]> response = restTemplate.getForEntity(url, Produto[].class);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void quandoBuscarProdutoPorIdEntaoProdutoRetornado() {
        // Arrange
        String url = "/produtos/" + produtoId;

        // Act
        ResponseEntity<Produto> response = restTemplate.getForEntity(url, Produto.class);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void quandoAtualizarProdutoEntaoProdutoAtualizado() {
        // Arrange
        String url = "/produtos/" + produtoId;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        Produto produtoAtualizado = new Produto(null, "ProdutoAtualizado", 50.0, "DescricaoAtualizada", 500);
        HttpEntity<Produto> request = new HttpEntity<>(produtoAtualizado, headers);

        // Act
        ResponseEntity<Produto> response = restTemplate.exchange(url, HttpMethod.PUT, request, Produto.class);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void quandoDeletarProdutoEntaoProdutoDeletado() {
        // Arrange
        String url = "/produtos/" + produtoId;

        // Act
        ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.DELETE, null, Void.class);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

}