package com.motorliberacaocredito.infrastructure.mapper;


import com.motorliberacaocredito.domain.model.CampanhaResponse;
import com.motorliberacaocredito.domain.model.PropostaCampanhaModel;


    public class CampanhaResponseMapper {

        private CampanhaResponseMapper() {
        }

        public static CampanhaResponse toResponse(PropostaCampanhaModel model) {

            CampanhaResponse response = new CampanhaResponse();
            response.setId(model.getId());
            response.setNome(model.getNome());
            response.setMensagemCampanha(model.getMensagemCampanha());

            return response;
        }
    }
