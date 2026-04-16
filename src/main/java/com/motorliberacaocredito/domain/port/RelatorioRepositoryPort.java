package com.motorliberacaocredito.domain.port;

import com.motorliberacaocredito.domain.model.ClienteModel;

import java.util.List;

public interface RelatorioRepositoryPort {
    List<ClienteModel> buscarTodos();
}
