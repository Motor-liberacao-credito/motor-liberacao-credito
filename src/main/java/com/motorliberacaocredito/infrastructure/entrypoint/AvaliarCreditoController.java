package com.motorliberacaocredito.infrastructure.entrypoint;

import com.motorliberacaocredito.aplication.usecase.PostAvaliarCreditoClienteUseCase;
import com.motorliberacaocredito.aplication.usecase.input.AvaliarCreditoInput;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/credito")
@RequiredArgsConstructor
public class AvaliarCreditoController implements AvaliarApi{

    private final PostAvaliarCreditoClienteUseCase postAvaliarCreditoClienteUseCase;

    @Override
    public ResponseEntity<Boolean> avaliarCredito(
            @PathVariable String id,
            @RequestParam Double valorSolicitado
            ){
        AvaliarCreditoInput input = new AvaliarCreditoInput(
               id,
               BigDecimal.valueOf(valorSolicitado)
        );

         boolean resultado = postAvaliarCreditoClienteUseCase.execute(input);

         return ResponseEntity.ok(resultado);
    }
}
