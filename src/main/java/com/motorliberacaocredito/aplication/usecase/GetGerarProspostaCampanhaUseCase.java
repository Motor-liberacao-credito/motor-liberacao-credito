package com.motorliberacaocredito.aplication.usecase;

import com.motorliberacaocredito.domain.model.ClienteModel;
import com.motorliberacaocredito.domain.model.PropostaCampanhaModel;

import java.util.Optional;

public interface GetGerarProspostaCampanhaUseCase {
    PropostaCampanhaModel execute(String id);
}
