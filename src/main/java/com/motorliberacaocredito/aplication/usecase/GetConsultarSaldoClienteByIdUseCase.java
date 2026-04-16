package com.motorliberacaocredito.aplication.usecase;

import java.util.Optional;

import com.motorliberacaocredito.domain.model.ClienteModel;


public interface GetConsultarSaldoClienteByIdUseCase {

    Optional <ClienteModel> execute (String id);
}
