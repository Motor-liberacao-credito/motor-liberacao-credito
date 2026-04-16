package com.motorliberacaocredito.domain.model;

import java.math.BigDecimal;

public class RelatorioModel {

    private final String id;
    private final String nome;
    private final BigDecimal saldo;
    private final BigDecimal score;
    private final String mensagemCampanha;

    public RelatorioModel(String id, String nome, BigDecimal saldo, BigDecimal score, String mensagemCampanha) {
        this.id = id;
        this.nome = nome;
        this.saldo = saldo;
        this.score = score;
        this.mensagemCampanha = mensagemCampanha;
    }

    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public BigDecimal getScore() {
        return score;
    }

    public String getMensagemCampanha() {
        return mensagemCampanha;
    }
}
