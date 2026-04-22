package com.motorliberacaocredito.aplication.usecase;

import com.motorliberacaocredito.aplication.usecase.input.AvaliarCreditoInput;
import com.motorliberacaocredito.domain.model.ClienteModel;

import java.util.Optional;

public interface PostAvaliarCreditoClienteUseCase {

    boolean execute (AvaliarCreditoInput input);
}
