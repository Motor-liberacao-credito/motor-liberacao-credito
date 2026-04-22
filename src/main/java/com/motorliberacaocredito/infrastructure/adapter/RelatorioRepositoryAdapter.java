package com.motorliberacaocredito.infrastructure.adapter;

import com.motorliberacaocredito.domain.model.ClienteModel;
import com.motorliberacaocredito.domain.port.RelatorioRepositoryPort;
import com.motorliberacaocredito.infrastructure.mapper.ClienteMapper;
import com.motorliberacaocredito.infrastructure.persistence.entity.repository.ClienteJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RelatorioRepositoryAdapter implements RelatorioRepositoryPort {

    private final ClienteJpaRepository repository;
    private final ClienteMapper mapper;

    @Override
    public List<ClienteModel> buscarTodos() {
        return repository.findAll().stream().map(mapper::toModel).toList();

    }


}
