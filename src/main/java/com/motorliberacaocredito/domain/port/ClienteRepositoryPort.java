package com.motorliberacaocredito.domain.port;

import com.motorliberacaocredito.domain.model.ClienteModel;



import java.util.List;
import java.util.Optional;

public interface ClienteRepositoryPort {
   Optional <ClienteModel> buscarporId(String id);
   void salvar(ClienteModel cliente);


}
