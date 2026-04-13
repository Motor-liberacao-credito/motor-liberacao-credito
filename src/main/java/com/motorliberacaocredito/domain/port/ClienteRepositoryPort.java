package com.motorliberacaocredito.domain.port;

import com.motorliberacaocredito.domain.model.ClienteModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

public interface ClienteRepositoryPort {
   Optional <ClienteModel> buscarporId(String id);
}
