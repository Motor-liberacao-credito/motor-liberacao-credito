package com.motorliberacaocredito.aplication.usecase.impl;

import com.motorliberacaocredito.aplication.usecase.PostAvaliarCreditoClienteUseCase;
import com.motorliberacaocredito.aplication.usecase.input.AvaliarCreditoInput;
import com.motorliberacaocredito.domain.model.ClienteModel;
import com.motorliberacaocredito.domain.port.ClienteRepositoryPort;
import com.motorliberacaocredito.domain.service.ScoreCalculatorService;
import com.motorliberacaocredito.infrastructure.entrypoint.exception.ClienteNaoEncontradoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
//import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostAvaliarCreditoClienteUseCaseImpl implements PostAvaliarCreditoClienteUseCase
{
    private final ClienteRepositoryPort clienteRepositoryPort;
    private final ScoreCalculatorService scoreCalculatorService;

    @Override
    public boolean execute(AvaliarCreditoInput input){

            ClienteModel cliente = clienteRepositoryPort.buscarporId(input.getClienteId())
                    .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

            BigDecimal novoScore = scoreCalculatorService.calcularScore(cliente);

            double limitePermitido = cliente.getSaldo().doubleValue()* 0.35;


        if (limitePermitido > input.getValorSolicitado().doubleValue()) {
            throw new ClienteNaoEncontradoException(
                    String.format(
                            "Valor solicitado (%s) excede o limite permitido de 35%% do saldo (%s)",
                            input.getValorSolicitado(),
                            limitePermitido
                    )
            );
        }

        if (novoScore.compareTo(BigDecimal.valueOf(60)) < 0) {
            throw new ClienteNaoEncontradoException("Score insuficiente para aprovação de crédito");
        }

            //BigDecimal valorScore = BigDecimal.valueOf(60);

        return true;

    }


}
