package com.example.demo;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoRepository produtoRepository;

    @Autowired
    public ProdutoController(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    private String produtoNaoEncontradoMessage = "Produto não encontrado";

    @GetMapping
    public List<Produto> listarTodos() {
        return produtoRepository.findAll();
    }

    @GetMapping("/{id}")
    public Produto buscarProdutoPorId(@PathVariable Long id) {
        return produtoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(produtoNaoEncontradoMessage));
    }

    @PostMapping
    public Produto criarProduto(@Valid @RequestBody Produto produto) {
        return produtoRepository.save(produto);
    }

    @PutMapping("/{id}")
    public Produto atualizarProduto(@PathVariable Long id, @RequestBody @Valid Produto produto) {
        Produto existente = produtoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(produtoNaoEncontradoMessage));
        existente.setNome(produto.getNome());
        existente.setPreco(produto.getPreco());
        existente.setDescricao(produto.getDescricao());
        existente.setQuantidadeEmEstoque(produto.getQuantidadeEmEstoque());
        return produtoRepository.save(existente);
    }

    @DeleteMapping("/{id}")
    public void deletarProduto(@PathVariable @Validated Long id) {
        Produto existente = produtoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(produtoNaoEncontradoMessage));
        produtoRepository.delete(existente);
    }

}
