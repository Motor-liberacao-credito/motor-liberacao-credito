package com.motorliberacaocredito.infrastructure.entrypoint;

import com.motorliberacaocredito.aplication.usecase.GetConsultarSaldoClienteByIdUseCase;
import com.motorliberacaocredito.aplication.usecase.GetGerarProspostaCampanhaUseCase;
import com.motorliberacaocredito.aplication.usecase.PostAvaliarCreditoClienteUseCase;
import com.motorliberacaocredito.aplication.usecase.input.AvaliarCreditoInput;
import com.motorliberacaocredito.domain.model.CampanhaResponse;
import com.motorliberacaocredito.domain.model.ClienteModel;
import com.motorliberacaocredito.domain.model.ClienteResponse;
import com.motorliberacaocredito.infrastructure.mapper.CampanhaResponseMapper;
import com.motorliberacaocredito.infrastructure.mapper.ClienteResponseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/credito")
@RequiredArgsConstructor
public class CreditoController implements ClienteApi {

    private final GetConsultarSaldoClienteByIdUseCase getClienteByIdUseCase;



     @Override
    public ResponseEntity<ClienteResponse> consultarCliente(String id){
          ClienteModel model = getClienteByIdUseCase.execute(id)
                  .orElseThrow(() -> new RuntimeException(id));

          ClienteResponse response = ClienteResponseMapper.toResponse(model);
          response.setId(model.getId());
          response.setNome(model.getNome());
          response.setSaldo(model.getSaldo());
          response.setScore(model.getScore());

          return ResponseEntity.ok(response);

     }
















//    @GetMapping("cliente/{id}")
//    public ResponseEntity<ClienteResponse> getClienteById(@PathVariable String id){
//        return getClienteByIdUseCase.execute(id)
//                .map(ClienteResponseMapper::toResponse)
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
//    }


//    @PostMapping("/clientes/avaliar/{id}")
//    public ResponseEntity<?> avaliarCredito(
//            @PathVariable String id,
//            @RequestParam BigDecimal valorSolicitado) {
//
//        AvaliarCreditoInput input =
//                new AvaliarCreditoInput(id, valorSolicitado);
//
//       boolean aprovado = useCase.execute(input);
//
//        return ResponseEntity.ok(aprovado);
//    }






}
