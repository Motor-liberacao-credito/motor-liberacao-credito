package com.motorliberacaocredito.infrastructure.entrypoint;

import com.motorliberacaocredito.aplication.usecase.GetConsultarSaldoClienteByIdUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/credito")
@RequiredArgsConstructor
public class CreditoController {
    private final GetConsultarSaldoClienteByIdUseCase getClienteByIdUseCase;

    @GetMapping("cliente/{id}")
    public ResponseEntity<?> getClienteById(@PathVariable String id){
        return getClienteByIdUseCase.execute(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


}
