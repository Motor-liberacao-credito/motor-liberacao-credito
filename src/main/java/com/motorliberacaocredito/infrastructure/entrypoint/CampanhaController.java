package com.motorliberacaocredito.infrastructure.entrypoint;

import com.motorliberacaocredito.aplication.usecase.GetGerarProspostaCampanhaUseCase;
import com.motorliberacaocredito.domain.model.CampanhaResponse;
import com.motorliberacaocredito.domain.model.PropostaCampanhaModel;
import com.motorliberacaocredito.infrastructure.mapper.CampanhaResponseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/credito")
@RequiredArgsConstructor
public class CampanhaController implements CampanhaApi {

    private final GetGerarProspostaCampanhaUseCase gerarProspostaCampanhaUseCase;

    @Override
    public ResponseEntity<CampanhaResponse> gerarCampanha(String id){
        PropostaCampanhaModel model = gerarProspostaCampanhaUseCase.execute(id);

        CampanhaResponse response = CampanhaResponseMapper.toResponse(model);
        response.setId(model.getId());
        response.setNome(model.getNome());
        response.setMensagemCampanha(model.getMensagemCampanha());

        return ResponseEntity.ok(response);
    }
}
