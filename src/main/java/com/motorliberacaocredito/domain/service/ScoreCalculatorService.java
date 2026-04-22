
package com.motorliberacaocredito.domain.service;

import com.motorliberacaocredito.domain.model.ClienteModel;
import com.motorliberacaocredito.domain.model.TransacaoModel;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class ScoreCalculatorService {

    public BigDecimal calcularScore(ClienteModel cliente) {

        if (cliente.getTransacoes() == null || cliente.getTransacoes().isEmpty()) {
            return BigDecimal.ZERO;
        }

        int total = cliente.getTransacoes().size();
        int positivas = 0;

        for (TransacaoModel t : cliente.getTransacoes()) {
            if (t.isPositiva()) {
                positivas++;
            }
        }

        BigDecimal positivasBD = BigDecimal.valueOf(positivas);
        BigDecimal totalBD = BigDecimal.valueOf(total);

        return positivasBD
                .divide(totalBD, 2, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100));
    }
}
