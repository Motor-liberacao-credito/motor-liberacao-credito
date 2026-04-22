package com.motorliberacaocredito.aplication.usecase.input;

import java.math.BigDecimal;

public class AvaliarCreditoInput {

    private final String clienteId;
    private final BigDecimal valorSolicitado;

    public AvaliarCreditoInput(String clienteId, BigDecimal valorSolicitado) {
        this.clienteId = clienteId;
        this.valorSolicitado = valorSolicitado;
    }
    
    

    public String getClienteId(){
        return clienteId;
    }

    public BigDecimal getValorSolicitado(){
        return valorSolicitado;
    }


    public void setClienteId(String id) {
    }

    public void setValorSolicitado(BigDecimal valorSolicitado) {
    }
}
