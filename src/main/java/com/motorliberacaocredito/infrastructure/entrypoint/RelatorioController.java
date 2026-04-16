package com.motorliberacaocredito.infrastructure.entrypoint;

import com.motorliberacaocredito.aplication.usecase.GetRelatorioUseCase;
import com.motorliberacaocredito.domain.model.RelatorioCampanhaResponse;
import com.motorliberacaocredito.domain.model.RelatorioModel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/credito")
@RequiredArgsConstructor
public class RelatorioController implements RelatorioApi{

    private final GetRelatorioUseCase getRelatorioUseCase;

    @Override
    public ResponseEntity<List<RelatorioCampanhaResponse>> gerarRelatorioCampanha(){
        List<RelatorioModel> models = getRelatorioUseCase.execute();

        List<RelatorioCampanhaResponse> response =
                getRelatorioUseCase.execute()
                        .stream()
                        .map(this::toResponse)
                        .toList();

        return ResponseEntity.ok(response);

    }


    private RelatorioCampanhaResponse toResponse(RelatorioModel model) {

        RelatorioCampanhaResponse response = new RelatorioCampanhaResponse();
        response.setId(model.getId());
        response.setNome(model.getNome());
        response.setScore(model.getScore().doubleValue());
        response.setSaldo(model.getSaldo().doubleValue());
        response.setMensagemCampanha(model.getMensagemCampanha());

        return response;


    }


}
