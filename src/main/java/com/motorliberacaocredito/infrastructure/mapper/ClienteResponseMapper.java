package com.motorliberacaocredito.infrastructure.mapper;

import com.motorliberacaocredito.domain.model.ClienteModel;
import com.motorliberacaocredito.domain.model.ClienteResponse;

public class ClienteResponseMapper {

    public static ClienteResponse toResponse(ClienteModel model) {
            ClienteResponse response = new ClienteResponse();
            response.setId(model.getId());
            response.setNome(model.getNome());
            response.setSaldo(model.getSaldo());
            response.setScore(model.getScore());

            return response;
        }


    }
