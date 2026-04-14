package com.motorliberacaocredito.aplication.usecase.impl;

import com.motorliberacaocredito.aplication.usecase.GetConsultarSaldoClienteByIdUseCase;
import com.motorliberacaocredito.domain.model.ClienteModel;
import com.motorliberacaocredito.domain.model.TransacaoModel;
import com.motorliberacaocredito.domain.port.ClienteRepositoryPort;
import com.motorliberacaocredito.domain.service.ScoreCalculatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GetConsultarSaldoClienteByIdUseCaseImpl implements GetConsultarSaldoClienteByIdUseCase {

    private final ClienteRepositoryPort clienteRepositoryPort;
    private final ScoreCalculatorService scoreCalculatorService;

    @Override
    public Optional<ClienteModel> execute(String id){
        return clienteRepositoryPort.buscarporId(id).map(cliente -> {

            BigDecimal novoScore = scoreCalculatorService.calcularScore(cliente);
            cliente.setScore(novoScore);

            BigDecimal novoSaldo = cliente.getSaldo();
            for(TransacaoModel t: cliente.getTransacoes()){
                if(!t.isPositiva()){
                     novoSaldo = novoSaldo.subtract(t.getValortransacao());
                } else {
                    novoSaldo = novoSaldo.add(t.getValortransacao());
                }
            }

            cliente.setSaldo(novoSaldo);

            return cliente;
        });




    }

}
