package com.motorliberacaocredito.domain.model;

import java.math.BigDecimal;
import java.util.Date;

public class TransacaoModel {

    private Long idtransacao;
    private BigDecimal valortransacao;
    private Date data;
    private boolean positiva;
    private ClienteModel cliente;


    public TransacaoModel() {
    }


    public TransacaoModel(Long idtransacao, BigDecimal valortransacao, boolean positiva, Date data, ClienteModel cliente) {
        this.idtransacao = idtransacao;
        this.valortransacao = valortransacao;
        this.positiva = positiva;
        this.data = data;
        this.cliente = cliente;
    }

    public Long getIdtransacao() {
        return idtransacao;
    }

    public void setIdtransacao(Long idtransacao) {
        this.idtransacao = idtransacao;
    }

    public BigDecimal getValortransacao() {
        return valortransacao;
    }

    public void setValortransacao(BigDecimal valortransacao) {
        this.valortransacao = valortransacao;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public boolean isPositiva() {
        return positiva;
    }

    public void setPositiva(boolean positiva) {
        this.positiva = positiva;
    }
}
