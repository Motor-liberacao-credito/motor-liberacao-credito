package com.motorliberacaocredito.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "TRANSACAO")
public class TransacaoEntity {
    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long idtransacao;

     @Column(name = "VALORTRANSACAO", precision = 10, scale = 2)
     @DecimalMin(value = "0.00", inclusive = true, message = "A transação não pode ser negativa")
     private BigDecimal valortransacao;


    @CreatedDate
    @Column(name = "DATA", nullable = false, updatable = false)
    private LocalDateTime data;


    @Column(name = "POSITIVA")
    private boolean positiva;

    @ManyToOne
    @JoinColumn(name = "ID_CLIENTE")
    private ClienteEntity cliente;

}

