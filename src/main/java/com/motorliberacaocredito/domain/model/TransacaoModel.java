package com.motorliberacaocredito.domain.model;

import java.math.BigDecimal;
import java.util.Date;

public class TransacaoModel {

    private Long idtransacao;
    private BigDecimal valortransacao;
    private Date data;
    private boolean positiva;
    private ClienteModel cliente;

    public TransacaoModel(Long idtransacao, BigDecimal valortransacao, boolean positiva, Date data, ClienteModel cliente) {
        this.idtransacao = idtransacao;
        this.valortransacao = valortransacao;
        this.positiva = positiva;
        this.data = data;
        this.cliente = cliente;
    }
}
