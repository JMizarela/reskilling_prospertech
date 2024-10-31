package com.example.demo;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.Arrays;
import java.util.List;

class ProdutoControllerTests {

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private ProdutoController produtoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void quandoListarTodos_entaoRetornaTodosProdutos() {
        // Arrange
        Produto produto1 = new Produto(1L, "Produto1", 10.0, "Description1", 100);
        Produto produto2 = new Produto(2L, "Produto2", 20.0, "Description2", 200);
        when(produtoRepository.findAll()).thenReturn(Arrays.asList(produto1, produto2));

        // Act
        List<Produto> result = produtoController.listarTodos();

        // Assert
        assertEquals(2, result.size());
        verify(produtoRepository, times(1)).findAll();
    }

    @Test
    void quandoCriarProduto_entaoSalvaERetornaProduto() {
        // Arrange
        Produto produto = new Produto(3L, "Produto3", 30.0, "Description3", 300);
        when(produtoRepository.save(produto)).thenReturn(produto);

        // Act
        Produto result = produtoController.criarProduto(produto);

        // Assert
        assertEquals(produto, result);
        verify(produtoRepository, times(1)).save(produto);
    }

    @Test
    void quandoAtualizarProduto_entaoAtualizaERetornaProduto() {
        // Arrange
        Produto produto = new Produto(4L, "Produto4", 40.0, "Description4", 400);
        produto.setNome("Updated Name");
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));
        when(produtoRepository.save(produto)).thenReturn(produto);

        Produto updatedProduto = new Produto(4L, "Updated Name", 40.0, "Description4", 400);

        // Act
        Produto result = produtoController.atualizarProduto(1L, updatedProduto);

        // Assert
        assertEquals("Updated Name", result.getNome());
        verify(produtoRepository, times(1)).findById(1L);
        verify(produtoRepository, times(1)).save(produto);
    }

    @Test
    void quandoAtualizarProdutoEIdNaoExistir_entaoLancaExcecaoQuandoProdutoNaoEncontrado() {
        // Arrange
        when(produtoRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                produtoController.atualizarProduto(1L, new Produto(null, "Produto5", 50.0, "Description5", 500));
            }
        });

        assertEquals("Produto não encontrado", exception.getMessage());
        verify(produtoRepository, times(1)).findById(1L);
    }

    @Test
    void quandoDeletarProduto_entaoDeletaProduto() {
        // Arrange
        Produto produto = new Produto(6L, "Produto6", 60.0, "Description6", 600);
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));

        // Act
        produtoController.deletarProduto(1L);

        // Assert
        verify(produtoRepository, times(1)).findById(1L);
        verify(produtoRepository, times(1)).delete(produto);
    }

    @Test
    void quandoDeletarProdutoEIdNaoExistir_entaoLancaExcecaoQuandoProdutoNaoEncontrado() {
        // Arrange
        when(produtoRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            produtoController.deletarProduto(1L);
        });

        assertEquals("Produto não encontrado", exception.getMessage());
        verify(produtoRepository, times(1)).findById(1L);
    }

    @Test
    void quandoBuscarProdutoPorId_entaoRetornaProduto() {
        // Arrange
        Produto produto = new Produto(7L, "Produto7", 70.0, "Description7", 700);
        when(produtoRepository.findById(7L)).thenReturn(Optional.of(produto));

        // Act
        Produto result = produtoController.buscarProdutoPorId(7L);

        // Assert
        assertEquals(produto, result);
        verify(produtoRepository, times(1)).findById(7L);
    }

    @Test
    void quandoBuscarProdutoPorIdEIdNaoExistir_entaoLancaExcecao() {
        // Arrange
        when(produtoRepository.findById(8L)).thenReturn(Optional.empty());

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            produtoController.buscarProdutoPorId(8L);
        });

        assertEquals("Produto não encontrado", exception.getMessage());
        verify(produtoRepository, times(1)).findById(8L);
    }

}