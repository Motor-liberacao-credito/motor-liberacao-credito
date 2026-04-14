package com.motorliberacaocredito.infrastructure.adapter;

import com.motorliberacaocredito.domain.model.ClienteModel;
import com.motorliberacaocredito.domain.port.ClienteRepositoryPort;
import com.motorliberacaocredito.infrastructure.mapper.ClienteMapper;
import com.motorliberacaocredito.infrastructure.persistence.entity.repository.ClienteJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ClienteRepositoryAdapter implements ClienteRepositoryPort {

    private final ClienteJpaRepository repository;
    private final ClienteMapper mapper;

    @Override
    public Optional<ClienteModel> buscarporId(String id) {
        return repository.findById(id).map(mapper::toModel);

    }
}


