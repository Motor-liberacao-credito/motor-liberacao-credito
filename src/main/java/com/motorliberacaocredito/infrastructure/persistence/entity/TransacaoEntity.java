package com.motorliberacaocredito.infrastructure.persistence.entity;

import jakarta.persistence.*;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "TRANSACAO")
public class TransacaoEntity {
    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idtransacao;

    @Column(name = "VALORTRANSACAO", precision = 10, scale = 2)
    @DecimalMin(value = "0.00", inclusive = true, message = "A transação não pode ser negativa")
    private BigDecimal valortransacao;

    @Column(name = "DATA")
    private Date data;

    @Column(name = "POSITIVA")
    private boolean positiva;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private ClienteEntity CLIENTE;

}

