package com.motorliberacaocredito.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.*;


import java.math.BigDecimal;
import java.util.List;

/**
 * Entidade JPA que mapeia a tabela CLIENTES no banco H2.
 *
 * Separada do modelo de domínio (Cliente) para garantir
 * que as anotações JPA não "contaminem" o domínio de negócio.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "CLIENTES")
public class ClienteEntity {


    @Id
    @Column(name = "ID", nullable = false)
    private String id;

    @Column(name = "NOME", nullable = false)
    private String nome;

    @Column(name = "SALDO", nullable = false, precision = 19, scale = 2)
    private BigDecimal saldo;

    @Column(name = "SCORE", nullable = false)
    private Integer score;


    @OneToMany(mappedBy = "CLIENTE", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<TransacaoEntity> transacoes;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public List<TransacaoEntity> getTransacoes() {
        return transacoes;
    }

    public void setTransacoes(List<TransacaoEntity> transacoes) {
        this.transacoes = transacoes;
    }
}