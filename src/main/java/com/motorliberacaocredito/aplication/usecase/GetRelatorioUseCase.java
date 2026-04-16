package com.motorliberacaocredito.aplication.usecase;

import com.motorliberacaocredito.domain.model.RelatorioModel;

import java.util.List;
import java.util.Optional;

public interface GetRelatorioUseCase {
    List<RelatorioModel> execute();
}
