package com.motorliberacaocredito.infrastructure.dataloader;

import com.motorliberacaocredito.infrastructure.persistence.entity.ClienteEntity;
import com.motorliberacaocredito.infrastructure.persistence.entity.TransacaoEntity;
import com.motorliberacaocredito.infrastructure.persistence.entity.repository.ClienteJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
@AllArgsConstructor
public class MassDataLoader implements CommandLineRunner {

    private final ClienteJpaRepository clienteRepository;
    private final Random random = new Random();

    @Override
    public void run(String... args) {
        List<ClienteEntity> clientes = new ArrayList<>();

        for (int i = 1; i <= 1000; i++) {
            ClienteEntity cliente = new ClienteEntity();
            cliente.setId(String.valueOf(i));
            cliente.setNome("Cliente_" + i);

            // Saldo entre -5000 e +15000 para simular inadimplência e clientes saudáveis
            double saldoBase = (random.nextDouble() * 20000) - 5000;
            cliente.setSaldo(BigDecimal.valueOf(saldoBase).setScale(2, RoundingMode.HALF_UP));

            // Criar transações aleatórias
            List<TransacaoEntity> transacoes = new ArrayList<>();
            int qtdTransacoes = random.nextInt(30) + 10; // entre 10 e 40 transações

            for (int j = 0; j < qtdTransacoes; j++) {
                TransacaoEntity t = new TransacaoEntity();

                // Valor entre 0 e 50.000
                t.setValortransacao(BigDecimal.valueOf(random.nextDouble() * 50000).setScale(2, RoundingMode.HALF_UP));

                // Data nos últimos 2 anos
                t.setData(LocalDate.now().minusDays(random.nextInt(730)).atStartOfDay());

                // 60% chance de ser depósito (positiva), 40% débito
                t.setPositiva(random.nextDouble() < 0.6);

                t.setCliente(cliente);
                transacoes.add(t);
            }

            cliente.setTransacoes(transacoes);
            clientes.add(cliente);
        }

        clienteRepository.saveAll(clientes);

        long totalTransacoes = clientes.stream().mapToInt(c -> c.getTransacoes().size()).sum();
        long positivas = clientes.stream()
                .flatMap(c -> c.getTransacoes().stream())
                .filter(TransacaoEntity::isPositiva)
                .count();

        System.out.printf(">>> Inseridos %d clientes com %d transações (%.2f%% positivas)%n",
                clientes.size(),
                totalTransacoes,
                (positivas * 100.0) / totalTransacoes);
    }
}
