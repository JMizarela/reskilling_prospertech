package com.example.demo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;


@Entity
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @NotBlank(message = "Nome do produto é obrigatório")
    @Size(min = 2, message = "O nome do produto deve ter no mínimo 2 caracteres")
    private String nome;

    @NotNull(message = "Preço do produto é obrigatório")
    @Positive(message = "O preço do produto deve ser maior que zero")
    private Double preco;

    private String descricao;

    @NotNull(message = "A quantidade em estoque é obrigatória")
    @Min(value = 0, message = "A quantidade em estoque não pode ser negativa")
    private Integer quantidadeEmEstoque;

    public Produto() {
    }

    public Produto(
            final Long id,
            final String nome,
            final Double preco,
            final String descricao,
            final Integer quantidadeEmEstoque
    ) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.descricao = descricao;
        this.quantidadeEmEstoque = quantidadeEmEstoque;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(final @NotNull(message = "Nome do produto é obrigatório") @Size(min = 2, message = "O nome do produto deve ter no mínimo 2 caracteres") String nome) {
        this.nome = nome;
    }

    public  Double getPreco() {
        return preco;
    }

    public void setPreco(final @NotNull(message = "Preço do produto é obrigatório") @Positive(message = "O preço do produto deve ser maior que zero") Double preco) {
        this.preco = preco;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(final String descricao) {
        this.descricao = descricao;
    }

    public  Integer getQuantidadeEmEstoque() {
        return quantidadeEmEstoque;
    }

    public void setQuantidadeEmEstoque(final @NotNull(message = "A quantidade em estoque é obrigatória") @Min(value = 0, message = "A quantidade em estoque não pode ser negativa") Integer quantidadeEmEstoque) {
        this.quantidadeEmEstoque = quantidadeEmEstoque;
    }
}
