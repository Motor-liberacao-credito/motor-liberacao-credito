package com.motorliberacaocredito.aplication.usecase.impl;


import com.motorliberacaocredito.aplication.usecase.GetRelatorioUseCase;
import com.motorliberacaocredito.domain.model.ClienteModel;
import com.motorliberacaocredito.domain.model.PropostaCampanhaModel;
import com.motorliberacaocredito.domain.model.RelatorioModel;
import com.motorliberacaocredito.domain.port.ClienteRepositoryPort;
import com.motorliberacaocredito.domain.service.CampanhaService;
import com.motorliberacaocredito.domain.service.SaldoCalculatorService;
import com.motorliberacaocredito.domain.service.ScoreCalculatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetRelatorioUseCaseImpl
        implements GetRelatorioUseCase {

    private final ClienteRepositoryPort clienteRepositoryPort;
    private final ScoreCalculatorService scoreCalculatorService;
    private final SaldoCalculatorService saldoCalculatorService;
    private final CampanhaService campanhaService;

    @Override
    public List<RelatorioModel> execute() {

        return clienteRepositoryPort.buscarTodos()
                .stream()
                .map(cliente -> {

                    // ✅ Atualiza score e saldo ANTES da campanha
                    cliente.setScore(
                            scoreCalculatorService.calcularScore(cliente)
                    );

                    cliente.setSaldo(
                            saldoCalculatorService.calcularSaldo(cliente)
                    );

                    // ✅ Gera a proposta da campanha
                    PropostaCampanhaModel proposta =
                            campanhaService.gerarProposta(cliente);

                    return new Object[]{cliente, proposta};
                })
                // ✅ Filtra apenas clientes elegíveis
                .filter(obj -> {
                    PropostaCampanhaModel proposta =
                            (PropostaCampanhaModel) obj[1];

                    String mensagem = proposta.getMensagemCampanha().toLowerCase();

                    return !mensagem.contains("não está permitido");
                })
                // ✅ Mapeia para o RelatorioModel
                .map(obj -> {
                    ClienteModel cliente = (ClienteModel) obj[0];
                    PropostaCampanhaModel proposta =
                            (PropostaCampanhaModel) obj[1];

                    return new RelatorioModel(
                            cliente.getId(),
                            cliente.getNome(),
                            cliente.getSaldo(),
                            cliente.getScore(),
                            proposta.getMensagemCampanha()
                    );
                })
                .toList();
    }
}