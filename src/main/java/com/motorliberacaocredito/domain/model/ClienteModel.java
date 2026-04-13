package com.motorliberacaocredito.domain.model;

import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.List;

public class ClienteModel {

    private String id;
    private String nome;
    private BigDecimal saldo;
    private Integer score;
    //private List<TransacaoModel> transacoes = new ArrayList<>();

    public ClienteModel() {
    }

    public ClienteModel(Integer score, BigDecimal saldo, String nome, String id) {
        this.score = score;
        this.saldo = saldo;
        this.nome = nome;
        this.id = id;
    }

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

    //public List<TransacaoModel> getTransacoes() {
        //return transacoes;
    //}

    //public void setTransacoes(List<TransacaoModel> transacoes) {
       // this.transacoes = transacoes;
   // }
}
