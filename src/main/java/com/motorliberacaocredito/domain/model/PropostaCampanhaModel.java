package com.motorliberacaocredito.domain.model;

public class PropostaCampanhaModel {

    private final String id;
    private final String nome;
    private final String mensagemCampanha;

    public PropostaCampanhaModel(String id, String nome, String mensagemCampanha) {
        this.id = id;
        this.nome = nome;
        this.mensagemCampanha = mensagemCampanha;
    }

    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getMensagemCampanha() {
        return mensagemCampanha;
    }
}
