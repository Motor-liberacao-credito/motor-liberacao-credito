package com.motorliberacaocredito.domain.service;

import com.motorliberacaocredito.domain.model.ClienteModel;
import com.motorliberacaocredito.domain.model.PropostaCampanhaModel;
import com.motorliberacaocredito.domain.model.TransacaoModel;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CampanhaService {

    private static final BigDecimal SCORE_LIMITE = BigDecimal.valueOf(60);

    private BigDecimal preverCapacidadePagamento(ClienteModel cliente) {

        List<TransacaoModel> transacoes = cliente.getTransacoes();

        long positivas = transacoes.stream()
                .filter(TransacaoModel::isPositiva)
                .count();

        long total = transacoes.size();

        double proporcaoPositivas =
                total > 0 ? (double) positivas / total : 0.0;

        double b0 = 100.0;
        double b1 = 0.3;
        double b2 = 250.0;

        double y =
                b0
                        + b1 * cliente.getSaldo().doubleValue()
                        + b2 * proporcaoPositivas;

        return BigDecimal.valueOf(y)
                .setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * Gera a proposta de campanha com base nas regras atuais.
     */
    public PropostaCampanhaModel gerarProposta(ClienteModel cliente) {

        String mensagemCampanha;

        // Regra 1: Score maior que 60 não participa
        if (cliente.getScore() == null
                || cliente.getScore().compareTo(SCORE_LIMITE) > 0) {

            mensagemCampanha =
                    "Você não está permitido a participar da campanha devido ao seu score.";

        }
        // Regra 2: Saldo negativo não participa
        else if (cliente.getSaldo().compareTo(BigDecimal.ZERO) < 0) {

            mensagemCampanha =
                    "Você não está permitido a participar da campanha devido ao saldo negativo.";

        }
        // Cliente elegível
        else {

            BigDecimal capacidadePagamento =
                    preverCapacidadePagamento(cliente);

            mensagemCampanha = String.format(
                    "Olá %s, analisamos seu histórico e identificamos que você pode sair da inadimplência "
                            + "com parcelas de até R$ %.2f por mês. Aproveite nossa campanha especial!",
                    cliente.getNome(),
                    capacidadePagamento
            );
        }

        return new PropostaCampanhaModel(
                cliente.getId(),
                cliente.getNome(),
                mensagemCampanha
        );
    }
}