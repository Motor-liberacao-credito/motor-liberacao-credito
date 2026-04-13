package com.motorliberacaocredito.aplication.usecase.impl;

import com.motorliberacaocredito.aplication.usecase.GetConsultarSaldoClienteByIdUseCase;
import com.motorliberacaocredito.domain.model.ClienteModel;
import com.motorliberacaocredito.domain.port.ClienteRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GetConsultarSaldoClienteByIdUseCaseImpl implements GetConsultarSaldoClienteByIdUseCase {

    private final ClienteRepositoryPort clienteRepositoryPort;

    @Override
    public Optional<ClienteModel> execute(String id){
        return clienteRepositoryPort.buscarporId(id);
    }

}
