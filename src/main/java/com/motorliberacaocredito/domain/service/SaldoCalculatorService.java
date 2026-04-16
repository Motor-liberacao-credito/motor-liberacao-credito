package com.motorliberacaocredito.domain.service;

import com.motorliberacaocredito.domain.model.ClienteModel;
import com.motorliberacaocredito.domain.model.TransacaoModel;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class SaldoCalculatorService {

    public BigDecimal calcularSaldo (ClienteModel cliente){

        BigDecimal novoSaldo = cliente.getSaldo();

        for(TransacaoModel t: cliente.getTransacoes()){
            if(!t.isPositiva()){
                novoSaldo = novoSaldo.subtract(t.getValortransacao());
            } else {
                novoSaldo = novoSaldo.add(t.getValortransacao());
            }
        }

        return novoSaldo;
    }


}
