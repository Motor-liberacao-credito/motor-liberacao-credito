package com.motorliberacaocredito.aplication.usecase;

import com.motorliberacaocredito.domain.model.RelatorioModel;

import java.util.List;


public interface GetRelatorioUseCase {
    List<RelatorioModel> execute();
}
