package com.motorliberacaocredito.aplication.usecase;

import com.motorliberacaocredito.domain.model.PropostaCampanhaModel;

public interface GetGerarCampanhaUseCase {
    PropostaCampanhaModel gerarCampanha(String clienteId);
}
